import datetime
from typing import List

from sqlalchemy import select, text

from api.database import new_session
from api.models import AudioFeatureTable, SegmentAudioFeatureTable, UserRecommendationTable, \
    UserRecommendationGenreTable, UserRecommendationTrackTable, UserNeuralNetworkConfigurationTable
from api.schemas import RecommendationCreateDTO


class GenreRepository:
    @classmethod
    async def check_genre_exists(cls, genre_id: int) -> bool:
        async with new_session() as session:
            params = {'id': genre_id}
            statement = text("""SELECT * FROM genre WHERE id = :id""")
            result = await session.execute(statement, params)
            genre = result.scalar_one_or_none()
            return genre is not None


class TrackRepository:
    @classmethod
    async def get_all_track_id(cls):
        async with new_session() as session:
            statement = text("""SELECT id FROM track""")
            result = await session.execute(statement)
            id_list = [row[0] for row in result.fetchall()]
            return id_list

    @classmethod
    async def get_track_filename(cls, track_id: int) -> str:
        async with new_session() as session:
            params = {'id': track_id}
            statement = text("""SELECT audio_filename FROM track WHERE id = :id""")
            result = await session.execute(statement, params)
            filename = result.scalar_one_or_none()
            return filename

    @classmethod
    async def get_track_count_by_genre(cls, genre_id: List[int]) -> int:
        async with new_session() as session:
            count = 0
            for genre in genre_id:
                params = {'genre_id': genre}
                statement = text("""SELECT COUNT(id) FROM track WHERE primary_genre_id = :genre_id""")
                result = await session.execute(statement, params)
                count += result.scalar_one_or_none()
            return count


class ExtractionTypeRepository:
    @classmethod
    async def get_extraction_type(cls, extraction_type_id: int):
        async with new_session() as session:
            params = {'id': extraction_type_id}
            statement = text("""SELECT start_delta, segment_duration FROM track_audio_feature_extraction_type WHERE 
            id = :id""")
            result = await session.execute(statement, params)
            extraction_type = result.fetchone()
            return extraction_type


class AudioFeatureRepository:
    @classmethod
    async def get_audio_feature_id(cls, track_id: int, extraction_type_id: int) -> int:
        async with new_session() as session:
            statement = select(AudioFeatureTable).where(AudioFeatureTable.track_id == track_id,
                                                        AudioFeatureTable.extraction_type_id == extraction_type_id)
            result = await session.execute(statement)

            audio_feature = result.scalar_one_or_none()

            if audio_feature is None:
                audio_feature = AudioFeatureTable(track_id=track_id, extraction_type_id=extraction_type_id)
                session.add(audio_feature)
                await session.commit()
                await session.refresh(audio_feature)

            return audio_feature.id


class SegmentAudioFeatureRepository:
    @classmethod
    async def add_segment_audio_feature(cls, audio_feature_id, segment_number, data):
        async with new_session() as session:
            statement = select(SegmentAudioFeatureTable).filter_by(audio_feature_id=audio_feature_id,
                                                                   segment_number=segment_number)
            result = await session.execute(statement)

            segment_audio_feature = result.scalar_one_or_none()

            if segment_audio_feature is None:
                segment_audio_feature = SegmentAudioFeatureTable(audio_feature_id=audio_feature_id,
                                                                 segment_number=segment_number, data=data)
                session.add(segment_audio_feature)
                await session.commit()
            else:
                segment_audio_feature.data = data
                await session.commit()

    @classmethod
    async def check_all_records(cls):
        async with new_session() as session:
            statement = select(SegmentAudioFeatureTable)
            result = await session.execute(statement)

            segment_audio_features = result.scalars()

            errors = 0

            for segment_audio_feature in segment_audio_features:
                if len(segment_audio_feature.data) != 3011:  # expected value
                    errors += 1

            return errors

    @classmethod
    async def get_all_track_data(cls, extraction_type_id):
        async with new_session() as session:
            params = {'id': extraction_type_id}
            statement = text("""SELECT 
                                    taf.track_id,
                                    tsaf.data
                                FROM 
                                    public.track_segment_audio_feature tsaf
                                JOIN
                                    public.track_audio_feature taf ON taf.id = tsaf.track_audio_feature_id
                                WHERE
                                taf.track_audio_feature_extraction_type_id = :id""")
            result = await session.execute(statement, params)
            data = result.fetchall()
            return data


class UserDataRepository:
    @classmethod
    async def check_user_exists(cls, user_id: int) -> bool:
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT * FROM track WHERE id = :id""")
            result = await session.execute(statement, params)
            user = result.scalar_one_or_none()
            return user is not None

    @classmethod
    async def get_all_user_id(cls):
        async with new_session() as session:
            statement = text("""SELECT id FROM user_data""")
            result = await session.execute(statement)
            id_list = [row[0] for row in result.fetchall()]
            return id_list


class RatingRepository:
    @classmethod
    async def get_rating_from_user_track(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT track_id FROM user_track WHERE user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_album(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                        alt.track_id,
                                        sub.track_count
                                    FROM
                                        public.album_track alt
                                    JOIN 
                                        public.user_album ual ON alt.album_id = ual.album_id
                                        JOIN
                                        (
                                            SELECT
                                                album_id,
                                                COUNT(track_id) AS track_count
                                            FROM
                                                public.album_track
                                            GROUP BY 
                                                album_id
                                        ) sub ON alt.album_id = sub.album_id
                                    WHERE ual.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_artist(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    art.track_id,
                                    sub.track_count
                                FROM
                                    public.artist_track art
                                JOIN 
                                    public.user_artist uar ON art.artist_id = uar.artist_id
                                    JOIN
                                    (
                                        SELECT
                                            artist_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.artist_track
                                        GROUP BY 
                                            artist_id
                                    ) sub ON art.artist_id = sub.artist_id
                                WHERE uar.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_playlist(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    pt.track_id,
                                    sub.track_count
                                FROM
                                    public.playlist_track pt
                                JOIN 
                                    public.user_playlist_rating up ON pt.playlist_id = up.playlist_id
                                    JOIN
                                    (
                                        SELECT
                                            playlist_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.playlist_track
                                        GROUP BY 
                                            playlist_id
                                    ) sub ON pt.playlist_id = sub.playlist_id
                                WHERE up.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_track_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT track_id, user_rating_id FROM user_track_rating WHERE user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_track_history(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    uth.track_id
                                FROM 
                                    public.user_track_history uth
                                WHERE uth.user_id = :id
                                ORDER BY
                                    uth.listen_date DESC
                                LIMIT 100""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_album_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    alt.track_id,
                                    ualr.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.album_track alt
                                JOIN 
                                    public.user_album_rating ualr ON alt.album_id = ualr.album_id
                                    JOIN
                                    (
                                        SELECT
                                            album_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.album_track
                                        GROUP BY 
                                            album_id
                                    ) sub ON alt.album_id = sub.album_id
                                WHERE ualr.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_artist_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    art.track_id,
                                    uarr.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.artist_track art
                                JOIN 
                                    public.user_artist_rating uarr ON art.artist_id = uarr.artist_id
                                    JOIN
                                    (
                                        SELECT
                                            artist_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.artist_track
                                        GROUP BY 
                                            artist_id
                                    ) sub ON art.artist_id = sub.artist_id
                                WHERE uarr.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_playlist_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    pt.track_id,
                                    upr.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.playlist_track pt
                                JOIN 
                                    public.user_playlist_rating upr ON pt.playlist_id = upr.playlist_id
                                    JOIN
                                    (
                                        SELECT
                                            playlist_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.playlist_track
                                        GROUP BY 
                                            playlist_id
                                    ) sub ON pt.playlist_id = sub.playlist_id
                                WHERE upr.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating

    @classmethod
    async def get_rating_from_user_recommendation_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    urt.track_id,
                                    ur.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.user_recommendation_track urt
                                JOIN 
                                    public.user_recommendation ur ON ur.id = urt.user_recommendation_id
                                JOIN
                                    (
                                        SELECT
                                            user_recommendation_id,
                                            COUNT(track_id) AS track_count
                                        FROM
                                            public.user_recommendation_track
                                        GROUP BY 
                                            user_recommendation_id
                                    ) sub ON urt.user_recommendation_id = sub.user_recommendation_id
                                WHERE ur.user_id = :id""")
            result = await session.execute(statement, params)
            rating = result.fetchall()
            return rating


class NeuralNetworkRepository:
    @classmethod
    async def save_configuration(cls, user_id: int, extraction_type_id: int, model_config, model_weights):
        async with new_session() as session:
            statement = select(UserNeuralNetworkConfigurationTable).filter_by(
                user_id=user_id,
                track_audio_feature_extraction_type_id=extraction_type_id)
            result = await session.execute(statement)

            configuration = result.scalar_one_or_none()

            if configuration is None:
                configuration = UserNeuralNetworkConfigurationTable(
                    user_id=user_id, track_audio_feature_extraction_type_id=extraction_type_id,
                    training_date=datetime.datetime.utcnow(), model_config=model_config, model_weights=model_weights)
                session.add(configuration)
                await session.commit()
            else:
                configuration.training_date = datetime.datetime.utcnow()
                configuration.model_config = model_config
                configuration.model_weights = model_weights
                await session.commit()

    @classmethod
    async def load_configuration(cls, user_id: int, extraction_type_id: int):
        async with new_session() as session:
            statement = select(UserNeuralNetworkConfigurationTable).where(
                UserNeuralNetworkConfigurationTable.user_id == user_id,
                UserNeuralNetworkConfigurationTable.track_audio_feature_extraction_type_id == extraction_type_id)
            result = await session.execute(statement)

            configuration = result.scalar_one_or_none()
            return configuration

    @classmethod
    async def save_recommendation(cls, recommendationCreateDTO: RecommendationCreateDTO, recommendation, config_id):
        async with new_session() as session:
            user_recommendation = UserRecommendationTable(user_id=recommendationCreateDTO.userId,
                                                          user_neural_network_configuration_id=config_id,
                                                          user_rating_id=0,
                                                          familiarity_percentage=
                                                          recommendationCreateDTO.familiarityPercentage,
                                                          creation_date=datetime.datetime.utcnow()
                                                          )
            session.add(user_recommendation)
            await session.commit()
            await session.refresh(user_recommendation)

            for genre in recommendationCreateDTO.genreId:
                user_recommendation_genre = UserRecommendationGenreTable(user_recommendation_id=user_recommendation.id,
                                                                         genre_id=genre)
                session.add(user_recommendation_genre)
                await session.commit()

            for i in range(len(recommendation)):
                user_recommendation_track = UserRecommendationTrackTable(user_recommendation_id=user_recommendation.id,
                                                                         track_id=recommendation[i],
                                                                         track_number_in_recommendation=i + 1)
                session.add(user_recommendation_track)
                await session.commit()

            return user_recommendation.id
