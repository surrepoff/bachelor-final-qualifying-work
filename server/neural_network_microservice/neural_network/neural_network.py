import pickle
import random

import numpy as np
from keras.models import model_from_json
from sklearn.model_selection import train_test_split
from tensorflow.keras import Sequential
from tensorflow.keras.layers import Dense, Input, Flatten, GaussianDropout

from api.repository import SegmentAudioFeatureRepository, NeuralNetworkRepository, TrackRepository
from api.schemas import RecommendationCreateDTO
from neural_network.track_rating import TrackRatingService


class NeuralNetwork:
    @classmethod
    async def get_training_data(cls, user_id: int, extraction_type_id: int):
        x = []
        y = []

        track_rating = await TrackRatingService.get_track_rating(user_id)
        if len(track_rating) == 0:
            track_id_list = await TrackRepository.get_all_track_id()
            random.shuffle(track_id_list)
            for i in range(len(track_id_list)):
                if len(track_rating) > len(track_id_list) / 10:
                    break
                track_rating[track_id_list[i]] = 0

        track_data = await SegmentAudioFeatureRepository.get_all_track_data(extraction_type_id)

        for track in track_data:
            if track.track_id in track_rating:
                result = track_rating[track.track_id]
                x.append(track.data)
                y.append(result)

        return x, y

    @classmethod
    async def get_track_data(cls, user_id: int, extraction_type_id: int):
        track_id = []
        data = []
        is_familiar = []

        track_rating = await TrackRatingService.get_track_rating(user_id)
        track_data = await SegmentAudioFeatureRepository.get_all_track_data(extraction_type_id)

        for track in track_data:
            track_id.append(track.track_id)
            data.append(track.data)
            if track.track_id in track_rating:
                is_familiar.append(True)
            else:
                is_familiar.append(False)

        return track_id, data, is_familiar

    @classmethod
    async def train_neural_network(cls, user_id: int, extraction_type_id: int):
        x, y = await cls.get_training_data(user_id, extraction_type_id)

        x = np.array(x)
        y = np.array(y)

        x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.3)

        model = Sequential()
        model.add(Input(shape=(3011,)))
        model.add(Flatten())
        model.add(Dense(3011, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(1024, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(256, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(64, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(16, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(4, activation='relu'))
        model.add(GaussianDropout(0.2))
        model.add(Dense(1, activation='sigmoid'))

        model.compile(optimizer='sgd', loss='binary_crossentropy', metrics=['accuracy'])

        model.fit(x_train, y_train, epochs=10, batch_size=32, validation_data=(x_test, y_test))

        scores = model.evaluate(x_test, y_test, verbose=0)
        print("Точность модели: %.2f%%" % (scores[1] * 100))

        model_config = model.to_json()
        model_weights = model.get_weights()

        model_weights_serialized = pickle.dumps(model_weights)

        await NeuralNetworkRepository.save_configuration(user_id, extraction_type_id,
                                                         model_config, model_weights_serialized)

    @classmethod
    async def get_recommendation(cls, recommendationCreateDTO: RecommendationCreateDTO):
        configuration = await NeuralNetworkRepository.load_configuration(recommendationCreateDTO.userId,
                                                                         recommendationCreateDTO.extractionTypeId)

        if configuration is None:
            await cls.train_neural_network(recommendationCreateDTO.userId, recommendationCreateDTO.extractionTypeId)
            configuration = await NeuralNetworkRepository.load_configuration(recommendationCreateDTO.userId,
                                                                             recommendationCreateDTO.extractionTypeId)

        print(type(configuration.model_config))

        model = model_from_json(configuration.model_config)

        model_weights_serialized = configuration.model_weights

        model_weights = pickle.loads(model_weights_serialized)
        model.set_weights(model_weights)

        model.compile(optimizer='sgd', loss='binary_crossentropy', metrics=['accuracy'])

        track_id, data, is_familiar = await cls.get_track_data(recommendationCreateDTO.userId,
                                                               recommendationCreateDTO.extractionTypeId)

        predictions = model.predict(np.array(data))
        # print(predictions)

        predicted_track_rating = {}
        is_track_familiar = {}

        for i in range(len(predictions)):
            is_track_familiar[track_id[i]] = is_familiar[i]
            if track_id[i] in predicted_track_rating:
                predicted_track_rating[track_id[i]] += predictions[i]
            else:
                predicted_track_rating[track_id[i]] = predictions[i]

        track_id_sorted_by_rating = sorted(predicted_track_rating, key=predicted_track_rating.get, reverse=True)

        recommendation = []

        genres_track_count = await TrackRepository.get_track_count_by_genre(recommendationCreateDTO.genreId)
        recommendation_size = min(recommendationCreateDTO.size, genres_track_count)

        counter = 0

        while len(recommendation) < recommendation_size:
            for track_id in track_id_sorted_by_rating:
                if len(recommendation) >= recommendation_size:
                    break

                random_number = random.random()

                if track_id in recommendation:
                    continue

                if track_id in is_track_familiar:
                    if random_number < recommendationCreateDTO.familiarityPercentage:
                        recommendation.append(track_id)
                else:
                    if random_number < 1 - recommendationCreateDTO.familiarityPercentage:
                        recommendation.append(track_id)

                counter += 1
                if counter >= recommendation_size * 1000:
                    break

        random.shuffle(recommendation)

        return await NeuralNetworkRepository.save_recommendation(recommendationCreateDTO,
                                                                 recommendation, configuration.id)
