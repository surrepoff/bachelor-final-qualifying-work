from datetime import datetime
from sqlalchemy import Integer, Column, String, DateTime, Text, TIMESTAMP
from sqlalchemy.orm import DeclarativeBase, Mapped, mapped_column


class Model(DeclarativeBase):
    pass


class UserDataTable(Model):
    __tablename__ = "user_data"

    id = Column('id', Integer, primary_key=True, index=True)
    username = Column('username', Text, nullable=False)
    email = Column('email', Text, nullable=False)
    password = Column('password', Text, nullable=False)
    nickname = Column('nickname', Text, nullable=False)
    registration_date = Column('registration_date', DateTime, nullable=False)
    last_update_date = Column('last_update_date', DateTime, nullable=False)
