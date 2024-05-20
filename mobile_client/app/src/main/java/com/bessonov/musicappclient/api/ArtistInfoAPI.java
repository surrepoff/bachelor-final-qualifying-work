package com.bessonov.musicappclient.api;

import com.bessonov.musicappclient.dto.ArtistInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArtistInfoAPI {
    @GET("/api/artistInfo/get/all")
    Call<List<ArtistInfoDTO>> getAll();

    @POST("/api/artistInfo/get/byArtistId")
    Call<ArtistInfoDTO> getByAlbumId(@Body int artistId);

    @POST("/api/artistInfo/get/byArtistId/list")
    Call<List<ArtistInfoDTO>> getByAlbumIdList(@Body List<Integer> artistIdList);
}
