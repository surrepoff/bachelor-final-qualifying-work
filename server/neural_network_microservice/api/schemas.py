from typing import List
from pydantic import BaseModel


class IdList(BaseModel):
    id_list: List[int]


class RecommendationCreateDTO(BaseModel):
    userId: int
    extractionTypeId: int
    size: int
    genreId: List[int]
    familiarityPercentage: float


class RecommendationResponseDTO(BaseModel):
    id: int
