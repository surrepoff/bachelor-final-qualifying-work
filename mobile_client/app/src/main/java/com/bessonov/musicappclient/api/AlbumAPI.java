package com.bessonov.musicappclient.api;

import com.bessonov.musicappclient.dto.AlbumInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlbumAPI {
    @GET("/album/get/all")
    Call<List<AlbumInfoDTO>> getAll();

    @POST("/album/get/byAlbumId")
    Call<AlbumInfoDTO> getByAlbumId(@Body int albumId);

    @POST("/album/get/byAlbumId/list")
    Call<List<AlbumInfoDTO>> getByAlbumIdList(@Body List<Integer> albumIdList);
}
