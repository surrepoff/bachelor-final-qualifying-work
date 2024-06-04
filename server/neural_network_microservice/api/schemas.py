from typing import List

from pydantic import BaseModel
from datetime import datetime


class IdList(BaseModel):
    id_list: List[int]


class RecommendationCreateDTO(BaseModel):
    userId: int
    size: int
    genreId: List[int]
    familiarityPercentage: float
