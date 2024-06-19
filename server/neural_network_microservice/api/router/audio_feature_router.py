from fastapi import APIRouter

from api.repository import ExtractionTypeRepository, TrackRepository, SegmentAudioFeatureRepository
from api.schemas import IdList, StatusResponseDTO
from audio_feature.audio_feature_extractor import AudioFeatureExtractor

router = APIRouter(
    prefix="/audio_feature",
    tags=["Audio Feature extraction"]
)


@router.get("/get/track/{track_id}/{extraction_type_id}")
async def get_audio_feature_by_track_id(track_id: int, extraction_type_id: int):
    extraction_type = await ExtractionTypeRepository.get_extraction_type(extraction_type_id)
    if extraction_type is None:
        return StatusResponseDTO(status="no extraction type")

    adh = AudioFeatureExtractor(extraction_type.start_delta, extraction_type.segment_duration)

    status = await adh.extract_audio_features_from_track(track_id, extraction_type_id)

    return StatusResponseDTO(status=status)


@router.post("/get/list/{extraction_type_id}")
async def get_audio_feature_by_track_id_list(id_list: IdList, extraction_type_id: int):
    extraction_type = await ExtractionTypeRepository.get_extraction_type(extraction_type_id)
    if extraction_type is None:
        return StatusResponseDTO(status="no extraction type")

    adh = AudioFeatureExtractor(extraction_type.start_delta, extraction_type.segment_duration)

    status = "success"
    error = 0
    error_id = []
    for track_id in id_list.id_list:
        track_status = await adh.extract_audio_features_from_track(track_id, extraction_type_id)
        if track_status != "success":
            error += 1
            error_id.append(track_id)
            status = str(error) + "error(s): " + str(error_id)

    return StatusResponseDTO(status=status)


@router.get("/get/all/{extraction_type_id}")
async def get_audio_feature_all(extraction_type_id: int):
    extraction_type = await ExtractionTypeRepository.get_extraction_type(extraction_type_id)
    if extraction_type is None:
        return StatusResponseDTO(status="no extraction type")

    adh = AudioFeatureExtractor(extraction_type.start_delta, extraction_type.segment_duration)

    id_list = await TrackRepository.get_all_track_id()

    status = "success"
    error = 0
    error_id = []
    for track_id in id_list:
        track_status = await adh.extract_audio_features_from_track(track_id, extraction_type_id)
        if track_status != "success":
            error += 1
            error_id.append(track_id)
            status = str(error) + "error(s): " + str(error_id)

    return StatusResponseDTO(status=status)


@router.get("/check")
async def check_records():
    result = await SegmentAudioFeatureRepository.check_all_records()
    return StatusResponseDTO(status=result)
