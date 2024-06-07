from fastapi import APIRouter

from api.repository import UserDataRepository
from api.schemas import RecommendationCreateDTO, RecommendationResponseDTO
from neural_network.neural_network import NeuralNetwork
from neural_network.track_rating import TrackRatingService

router = APIRouter(
    prefix="/recommendation",
    tags=["Recommendations for user"]
)


@router.post("/get")
async def get_user_recommendation(recommendationCreateDTO: RecommendationCreateDTO):
    if not await UserDataRepository.check_user_exists(recommendationCreateDTO.userId):
        recommendationResponseDTO = RecommendationResponseDTO(id=-1)
        return recommendationResponseDTO

    recommendation_id = await NeuralNetwork.get_recommendation(recommendationCreateDTO)
    recommendationResponseDTO = RecommendationResponseDTO(id=recommendation_id)

    return recommendationResponseDTO


@router.get("/rating/{user_id}")
async def get_track_rating(user_id: int):
    rating = await TrackRatingService.get_track_rating(user_id)
    return rating
