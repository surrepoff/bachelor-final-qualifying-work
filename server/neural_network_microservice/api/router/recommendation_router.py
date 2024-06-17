from fastapi import APIRouter

from api.repository import UserDataRepository, ExtractionTypeRepository, GenreRepository
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

    extraction_type = await ExtractionTypeRepository.get_extraction_type(recommendationCreateDTO.extractionTypeId)
    if extraction_type is None:
        recommendationResponseDTO = RecommendationResponseDTO(id=-1)
        return recommendationResponseDTO

    if recommendationCreateDTO.size < 1 or recommendationCreateDTO.size > 20:
        recommendationCreateDTO.size = 10

    if recommendationCreateDTO.familiarityPercentage < 0 or recommendationCreateDTO.familiarityPercentage > 1:
        recommendationCreateDTO.familiarityPercentage = 0.5

    genre_set = set()
    for genre_id in recommendationCreateDTO.genreId:
        if await GenreRepository.check_genre_exists(genre_id):
            genre_set.add(genre_id)

    genre_list = []
    for genre_id in genre_set:
        genre_list.append(genre_id)

    if len(genre_list) == 0:
        recommendationResponseDTO = RecommendationResponseDTO(id=-1)
        return recommendationResponseDTO

    recommendation_id = await NeuralNetwork.get_recommendation(recommendationCreateDTO)
    recommendationResponseDTO = RecommendationResponseDTO(id=recommendation_id)

    return recommendationResponseDTO


@router.get("/rating/{user_id}")
async def get_track_rating(user_id: int):
    rating = await TrackRatingService.get_track_rating(user_id)
    return rating
