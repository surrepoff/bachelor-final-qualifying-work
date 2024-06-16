from sqlalchemy import Integer, Column, DateTime, Float, JSON, LargeBinary
from sqlalchemy.dialects.postgresql import ARRAY
from sqlalchemy.orm import DeclarativeBase


class Model(DeclarativeBase):
    pass


class AudioFeatureTable(Model):
    __tablename__ = "track_audio_feature"

    id = Column('id', Integer, primary_key=True, index=True)
    track_id = Column('track_id', Integer, nullable=False)
    extraction_type_id = Column('track_audio_feature_extraction_type_id', Integer, nullable=False)


class SegmentAudioFeatureTable(Model):
    __tablename__ = "track_segment_audio_feature"

    id = Column('id', Integer, primary_key=True, index=True)
    audio_feature_id = Column('track_audio_feature_id', Integer, nullable=False)
    segment_number = Column('segment_number', Integer, nullable=False)
    data = Column('data', ARRAY(Float), nullable=False)


class UserNeuralNetworkConfigurationTable(Model):
    __tablename__ = "user_neural_network_configuration"

    id = Column('id', Integer, primary_key=True, index=True)
    user_id = Column('user_id', Integer, nullable=False)
    track_audio_feature_extraction_type_id = Column('track_audio_feature_extraction_type_id',
                                                    Integer, nullable=False)
    training_date = Column('training_date', DateTime, nullable=False)
    model_config = Column('model_config', JSON, nullable=False)
    model_weights = Column('model_weights', LargeBinary, nullable=False)


class UserRecommendationTable(Model):
    __tablename__ = "user_recommendation"

    id = Column('id', Integer, primary_key=True, index=True)
    user_id = Column('user_id', Integer, nullable=False)
    user_neural_network_configuration_id = Column('user_neural_network_configuration_id', Integer, nullable=False)
    user_rating_id = Column('user_rating_id', Integer, nullable=False)
    familiarity_percentage = Column('familiarity_percentage', Float, nullable=False)
    creation_date = Column('creation_date', DateTime, nullable=False)


class UserRecommendationGenreTable(Model):
    __tablename__ = "user_recommendation_genre"

    user_recommendation_id = Column('user_recommendation_id', Integer, primary_key=True, index=True)
    genre_id = Column('genre_id', Integer, primary_key=True, nullable=False)


class UserRecommendationTrackTable(Model):
    __tablename__ = "user_recommendation_track"

    user_recommendation_id = Column('user_recommendation_id', Integer, primary_key=True, index=True)
    track_id = Column('track_id', Integer, primary_key=True, nullable=False)
    track_number_in_recommendation = Column('track_number_in_recommendation', Integer, nullable=False)
