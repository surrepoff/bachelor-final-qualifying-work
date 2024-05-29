from fastapi import APIRouter, Depends

from api.repository import UserDataRepository
from api.schemas import UserDataSchema, UserDataAddSchema

router = APIRouter(
    prefix="/user",
    tags=["User info"]
)


@router.get("/get/all")
async def get_all_users():
    users = await UserDataRepository.get_all_users()
    return users


@router.post("/add")
async def get_user(user: UserDataAddSchema = Depends()):
    new_user_id = await UserDataRepository.add_user(user)
    return {"id": new_user_id}
