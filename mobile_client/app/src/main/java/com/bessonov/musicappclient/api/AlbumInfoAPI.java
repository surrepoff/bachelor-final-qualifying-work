package com.bessonov.musicappclient.api;

import com.bessonov.musicappclient.dto.AlbumInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlbumInfoAPI {
    @GET("/api/albumInfo/get/all")
    Call<List<AlbumInfoDTO>> getAll();

    @POST("/api/albumInfo/get/byAlbumId")
    Call<AlbumInfoDTO> getByAlbumId(@Body int albumId);

    @POST("/api/albumInfo/get/byAlbumId/list")
    Call<List<AlbumInfoDTO>> getByAlbumIdList(@Body List<Integer> albumIdList);
}
