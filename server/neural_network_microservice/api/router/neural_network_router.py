from fastapi import APIRouter

from api.repository import UserDataRepository, ExtractionTypeRepository
from api.schemas import IdList
from neural_network.neural_network import NeuralNetwork

router = APIRouter(
    prefix="/neural_network",
    tags=["User Neural Network"]
)


@router.get("/train/user/{user_id}/{extraction_type_id}")
async def get_neural_network_by_user_id(user_id: int, extraction_type_id: int):
    if not await UserDataRepository.check_user_exists(user_id):
        return {"status": "No user"}

    if await ExtractionTypeRepository.get_extraction_type(extraction_type_id) is None:
        return {"status": "No extraction type"}

    await NeuralNetwork.train_neural_network(user_id, extraction_type_id)
    return {"status": "success"}


@router.post("/train/list/{extraction_type_id}")
async def get_neural_network_by_user_id_list(id_list: IdList, extraction_type_id: int):
    if await ExtractionTypeRepository.get_extraction_type(extraction_type_id) is None:
        return {"status": "No extraction type"}

    status = "success"
    error = 0
    error_id = []
    for user_id in id_list.id_list:
        if not await UserDataRepository.check_user_exists(user_id):
            error += 1
            error_id.append(user_id)
            status = str(error) + "error(s): " + str(error_id)
        else:
            await NeuralNetwork.train_neural_network(user_id, extraction_type_id)

    return {"status": status}


@router.get("/train/all/{extraction_type_id}")
async def get_neural_network_all(extraction_type_id: int):
    if await ExtractionTypeRepository.get_extraction_type(extraction_type_id) is None:
        return {"status": "No extraction type"}

    id_list = await UserDataRepository.get_all_user_id()

    for user_id in id_list:
        await NeuralNetwork.train_neural_network(user_id, extraction_type_id)

    return {"status": "success"}
