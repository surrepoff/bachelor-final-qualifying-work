import json

from datetime import datetime

from sqlalchemy import select, text

from api.database import new_session
from api.models import AudioFeatureTable, SegmentAudioFeatureTable


class TrackRepository:
    @classmethod
    async def get_all_track_id(cls):
        async with new_session() as session:
            statement = text("""SELECT id FROM track""")
            result = await session.execute(statement)
            id_list = [row[0] for row in result.fetchall()]
            print(id_list)
            return id_list

    @classmethod
    async def get_track_filename(cls, track_id: int) -> str:
        async with new_session() as session:
            params = {'id': track_id}
            statement = text("""SELECT audio_filename FROM track WHERE id = :id""")
            result = await session.execute(statement, params)
            print(result)
            filename = result.scalar_one_or_none()
            print(filename)
            return filename


class ExtractionTypeRepository:
    @classmethod
    async def get_extraction_type(cls, extraction_type_id: int):
        async with new_session() as session:
            params = {'id': extraction_type_id}
            statement = text("""SELECT start_delta, segment_duration FROM track_audio_feature_extraction_type WHERE 
            id = :id""")
            result = await session.execute(statement, params)
            print(result)
            extraction_type = result.fetchone()
            print(extraction_type)
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
            print(result)
            user = result.scalar_one_or_none()
            print(user)
            return user is not None


class RatingRepository:
    @classmethod
    async def get_rating_from_user_track_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT track_id, user_rating_id FROM user_track_rating WHERE user_id = :id""")
            result = await session.execute(statement, params)
            print(result)
            rating = result.fetchall()
            print(rating)
            return rating

    @classmethod
    async def get_rating_from_user_track_history(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    utk.track_id
                                FROM 
                                    public.user_track_history utk
                                WHERE utk.user_id = :id
                                ORDER BY
                                    utk.listen_date DESC
                                LIMIT 100""")
            result = await session.execute(statement, params)
            print(result)
            rating = result.fetchall()
            print(rating)
            return rating

    @classmethod
    async def get_rating_from_user_album_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    alt.track_id,
                                    altr.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.album_track alt
                                JOIN 
                                    public.user_album_rating altr ON alt.album_id = altr.album_id
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
                                WHERE altr.user_id = :id""")
            result = await session.execute(statement, params)
            print(result)
            rating = result.fetchall()
            print(rating)
            return rating

    @classmethod
    async def get_rating_from_user_artist_rating(cls, user_id: int):
        async with new_session() as session:
            params = {'id': user_id}
            statement = text("""SELECT 
                                    art.track_id,
                                    artr.user_rating_id,
                                    sub.track_count
                                FROM
                                    public.artist_track art
                                JOIN 
                                    public.user_artist_rating artr ON art.artist_id = artr.artist_id
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
                                WHERE artr.user_id = :id""")
            result = await session.execute(statement, params)
            print(result)
            rating = result.fetchall()
            print(rating)
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
            print(result)
            rating = result.fetchall()
            print(rating)
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
            print(result)
            rating = result.fetchall()
            print(rating)
            return rating


class NeuralNetworkRepository:
    @classmethod
    async def save_configuration(cls, user_id: int, extraction_type_id: int, model_config, model_weights):
        async with new_session() as session:
            params = {'user_id': user_id, 'extraction_type_id': extraction_type_id}
            statement = text("""SELECT id FROM user_neural_network_configuration
                                WHERE user_id = :user_id AND
                                track_audio_feature_extraction_type_id = :extraction_type_id""")
            result = await session.execute(statement, params)
            conf_id = result.scalar_one_or_none()

            training_date = datetime.utcnow()
            if conf_id is None:
                params = {'user_id': user_id, 'extraction_type_id': extraction_type_id, "training_date": training_date,
                          'model_config': json.dumps(model_config), 'model_weights': model_weights}
                statement = text("""INSERT INTO user_neural_network_configuration
                                (user_id, track_audio_feature_extraction_type_id,
                                training_date, model_config, model_weights) 
                                VALUES (:user_id, :extraction_type_id, :training_date, :model_config, :model_weights)""")
                await session.execute(statement, params)
                await session.commit()
            else:
                params = {'conf_id': conf_id, 'user_id': user_id, 'extraction_type_id': extraction_type_id, "training_date": training_date,
                          'model_config': json.dumps(model_config), 'model_weights': model_weights}
                statement = text("""UPDATE user_neural_network_configuration
                                    SET training_date = :training_date,
                                        model_config = :model_config,
                                        model_weights = :model_weights
                                    WHERE id = :conf_id""")
                await session.execute(statement, params)
                await session.commit()

    @classmethod
    async def load_configuration(cls, user_id: int, extraction_type_id: int):
        async with new_session() as session:
            params = {'user_id': user_id, 'extraction_type_id': extraction_type_id}
            statement = text("""SELECT model_config, model_weights FROM user_neural_network_configuration
                                WHERE user_id = :user_id AND
                                      track_audio_feature_extraction_type_id = :extraction_type_id""")
            result = await session.execute(statement, params)
            conf = result.fetchone()
            return conf
