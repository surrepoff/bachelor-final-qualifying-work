package com.bessonov.musicappclient.api;

import com.bessonov.musicappclient.dto.ArtistInfoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ArtistAPI {
    @GET("/artist/get/all")
    Call<List<ArtistInfoDTO>> getAll();

    @POST("/artist/get/byArtistId")
    Call<ArtistInfoDTO> getByAlbumId(@Body int artistId);

    @POST("/artist/get/byArtistId/list")
    Call<List<ArtistInfoDTO>> getByAlbumIdList(@Body List<Integer> artistIdList);
}
