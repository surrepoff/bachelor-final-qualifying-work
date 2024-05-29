from audio_feature_extractor.AudioDataHandler import *
from api.router.recommendation_router import router as recommendation_router
from api.router.user_router import router as user_router
from fastapi import FastAPI, Request, status
from fastapi.responses import JSONResponse

app = FastAPI()

# Whitelisted IPs
WHITELISTED_IPS = ["localhost", "127.0.0.1", "192.168.1.59", "192.168.1.91"]


@app.middleware('http')
async def validate_ip(request: Request, call_next):
    # Get client IP
    ip = str(request.client.host)

    # Check if IP is allowed
    if ip not in WHITELISTED_IPS:
        data = {
            'message': f'IP {ip} is not allowed to access this resource.'
        }
        return JSONResponse(status_code=status.HTTP_400_BAD_REQUEST, content=data)

    # Proceed if IP is allowed
    return await call_next(request)

app.include_router(recommendation_router)
app.include_router(user_router)

if __name__ == "__main__":
    audio_file = '../../data/track/Tricky_Diesel-Yup.mp3'
    # audio_file = librosa.ex('trumpet')

    adh = AudioDataHandler()
    # adh.extract_audio_features_from_file(audio_file)
    adh._extract_audio_features_from_segment(audio_file, 0)
