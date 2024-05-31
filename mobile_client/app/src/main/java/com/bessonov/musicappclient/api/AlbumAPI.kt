package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.AlbumInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumAPI {
    @GET("/album/get/all")
    fun getAll(): Call<List<AlbumInfoDTO>>

    @POST("/album/get/byAlbumId")
    fun getByAlbumId(@Body albumId: Int): Call<AlbumInfoDTO>

    @POST("/album/get/byAlbumId/list")
    fun getByAlbumIdList(@Body albumIdList: List<Int>): Call<List<AlbumInfoDTO>>
}
