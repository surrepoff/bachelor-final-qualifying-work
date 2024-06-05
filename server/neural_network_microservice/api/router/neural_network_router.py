from fastapi import APIRouter

from api.schemas import IdList
from neural_network.neural_network import train_neural_network

router = APIRouter(
    prefix="/neural_network",
    tags=["User Neural Network"]
)


@router.get("/train/user/{user_id}/{extraction_type_id}")
async def get_neural_network_by_user_id(user_id: int, extraction_type_id: int):
    await train_neural_network(user_id, extraction_type_id)
    return {"user_id": user_id, "extraction_type_id": extraction_type_id}


@router.post("/train/list/{extraction_type_id}")
async def get_neural_network_by_user_id_list(id_list: IdList, extraction_type_id: int):
    return {"user_id": id_list, "extraction_type_id": extraction_type_id}


@router.get("/train/all/{extraction_type_id}")
async def get_neural_network_all(extraction_type_id: int):
    return {"user_id": "all", "extraction_type_id": extraction_type_id}
