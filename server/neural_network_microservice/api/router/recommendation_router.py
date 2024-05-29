from fastapi import APIRouter
from api.repository import UserDataRepository


router = APIRouter(
    prefix="/recommendation",
    tags=["Recommendations for user"]
)


@router.get("/get/{user_id}")
async def get_user_recommendation(user_id: int):
    result = await UserDataRepository.check_user_exists(user_id)
    return {"user_id": user_id, "exists": result}

