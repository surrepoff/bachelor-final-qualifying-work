from sqlalchemy import Integer, Column, String, DateTime, Text, TIMESTAMP, Float
from sqlalchemy.dialects.postgresql import ARRAY
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column


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
