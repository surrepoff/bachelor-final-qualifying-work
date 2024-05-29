import datetime

from sqlalchemy import select

from api.database import new_session
from api.models import UserDataTable
from api.schemas import UserDataSchema, UserDataAddSchema


class UserDataRepository:
    @classmethod
    async def check_user_exists(cls, user_id: int) -> bool:
        async with new_session() as session:
            query = select(UserDataTable).where(UserDataTable.id == user_id)
            result = await session.execute(query)
            user = result.scalar_one_or_none()
            return user is not None

    @classmethod
    async def get_all_users(cls):
        async with new_session() as session:
            query = select(UserDataTable)
            result = await session.execute(query)
            user_models = result.scalars().all()
            users = [UserDataSchema.from_orm(user_model) for user_model in user_models]
            return users

    @classmethod
    async def add_user(cls, user: UserDataAddSchema) -> int:
        async with new_session() as session:
            new_user = UserDataTable(
                id=user.id,
                username=user.username,
                email=user.email,
                password=user.password,
                nickname=user.nickname,
                registration_date=datetime.datetime.utcnow(),
                last_update_date=datetime.datetime.utcnow()
            )
            session.add(new_user)
            await session.flush()
            await session.commit()
            return new_user.id
