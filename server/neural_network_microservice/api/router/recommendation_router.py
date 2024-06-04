from fastapi import APIRouter
from api.repository import UserDataRepository
from api.schemas import RecommendationCreateDTO

router = APIRouter(
    prefix="/recommendation",
    tags=["Recommendations for user"]
)


@router.post("/get")
async def get_user_recommendation(recommendationCreateDTO: RecommendationCreateDTO):
    if not UserDataRepository.check_user_exists(recommendationCreateDTO.userId):
        return {"id": -1}
    return {"id": -1}
