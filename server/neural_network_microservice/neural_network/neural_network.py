import json

import numpy as np
import tensorflow as tf
import pickle
from tensorflow.keras import Sequential
from tensorflow.keras.layers import Dense, Input, Flatten, GaussianDropout
from sklearn.model_selection import train_test_split
from tensorflow.python.keras.models import model_from_json
from api.repository import SegmentAudioFeatureRepository, NeuralNetworkRepository
from neural_network.track_rating import TrackRatingService


async def get_training_data(user_id: int, extraction_type_id: int):
    x = []
    y = []

    track_rating = await TrackRatingService.get_track_rating(user_id)
    track_data = await SegmentAudioFeatureRepository.get_all_track_data(extraction_type_id)

    for track in track_data:
        if track[0] in track_rating:
            result = track_rating[track[0]]
            x.append(track.data)
            y.append(result)

    return x, y


async def train_neural_network(user_id: int, extraction_type_id: int):
    x, y = await get_training_data(user_id, extraction_type_id)

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


async def get_recommendation(user_id: int, extraction_type_id: int):
    configuration = await NeuralNetworkRepository.load_configuration(user_id, extraction_type_id)

    model_configuration = json.loads(configuration[0])
    model_weights_serialized = configuration[1]

    model = model_from_json(model_configuration)

    model_weights = pickle.loads(model_weights_serialized)
    model.set_weights(model_weights)

    model.compile(optimizer='sgd', loss='binary_crossentropy', metrics=['accuracy'])

    # predictions = model.predict(X_test)
    # print(predictions)

