from api.router.recommendation_router import router as recommendation_router
from api.router.audio_feature_router import router as audio_feature_router
from fastapi import FastAPI, Request, status
from fastapi.responses import JSONResponse

app = FastAPI()

# Whitelisted IPs
WHITELISTED_IPS = ["localhost", "127.0.0.1", "192.168.1.59", "192.168.1.91", "192.168.159.230"]


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
app.include_router(audio_feature_router)
