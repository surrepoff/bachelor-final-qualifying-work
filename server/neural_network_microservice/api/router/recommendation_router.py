from fastapi import APIRouter
from api.repository import UserDataRepository
from api.schemas import RecommendationCreateDTO
from neural_network.track_rating import TrackRatingService

router = APIRouter(
    prefix="/recommendation",
    tags=["Recommendations for user"]
)


@router.post("/get")
async def get_user_recommendation(recommendationCreateDTO: RecommendationCreateDTO):
    if not UserDataRepository.check_user_exists(recommendationCreateDTO.userId):
        return {"id": -1}
    return {"id": -1}


@router.get("/rating/{user_id}")
async def get_track_rating(user_id: int):
    rating = await TrackRatingService.get_track_rating(user_id)
    return rating