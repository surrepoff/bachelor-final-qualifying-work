from pydantic import BaseModel
from datetime import datetime


class UserDataSchema(BaseModel):
    id: int
    username: str
    email: str
    password: str
    nickname: str
    registration_date: datetime
    last_update_date: datetime

    class Config:
        from_attributes = True


class UserDataAddSchema(BaseModel):
    id: int
    username: str
    email: str
    password: str
    nickname: str

    class Config:
        from_attributes = True
