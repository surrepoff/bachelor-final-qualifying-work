package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.ArtistInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ArtistAPI {
    @GET("/artist/get/all")
    fun getAll(): Call<List<ArtistInfoDTO>>

    @POST("/artist/get/byArtistId")
    fun getByAlbumId(@Body artistId: Int): Call<ArtistInfoDTO>

    @POST("/artist/get/byArtistId/list")
    fun getByAlbumIdList(@Body artistIdList: List<Int>): Call<List<ArtistInfoDTO>>
}
