package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.ArtistInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArtistAPI {
    @GET("/artist/get/all")
    fun getAll(): Call<List<ArtistInfoDTO>>

    @GET("/artist/get/{artistId}")
    fun getByAlbumId(@Path("artistId") artistId: Int): Call<ArtistInfoDTO>

    @POST("/artist/get/list")
    fun getByAlbumIdList(@Body artistIdList: List<Int>): Call<List<ArtistInfoDTO>>
}
