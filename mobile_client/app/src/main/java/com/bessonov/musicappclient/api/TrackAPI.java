package com.bessonov.musicappclient.api;

import com.bessonov.musicappclient.dto.TrackInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TrackAPI {
    @GET("/track/get/all")
    Call<List<TrackInfoDTO>> getAll();

    @POST("/track/get/byTrackId")
    Call<TrackInfoDTO> getByTrackId(@Body int trackId);

    @POST("/track/get/byTrackId/list")
    Call<List<TrackInfoDTO>> getByTrackIdList(@Body List<Integer> trackIdList);
}
