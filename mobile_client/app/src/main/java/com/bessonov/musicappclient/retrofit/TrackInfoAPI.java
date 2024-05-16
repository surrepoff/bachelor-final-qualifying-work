package com.bessonov.musicappclient.retrofit;

import com.bessonov.musicappclient.dto.TrackInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TrackInfoAPI {
    @GET("/api/trackInfo/get/all")
    Call<List<TrackInfoDTO>> getAll();

    @POST("/api/trackInfo/get/byTrackId")
    Call<TrackInfoDTO> getByTrackId(@Body int trackId);

    @POST("/api/trackInfo/get/byTrackId/list")
    Call<List<TrackInfoDTO>> getByTrackIdList(@Body List<Integer> trackIdList);
}
