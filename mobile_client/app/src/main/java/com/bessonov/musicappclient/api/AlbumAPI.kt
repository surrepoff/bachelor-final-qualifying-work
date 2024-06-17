package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.AlbumInfoDTO
import com.bessonov.musicappclient.dto.UserAlbumDTO
import com.bessonov.musicappclient.dto.UserRatingDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumAPI {
    @GET("/album/get/all")
    fun getAll(): Call<List<AlbumInfoDTO>>

    @GET("/album/get/{albumId}")
    fun getByAlbumId(@Path("albumId") albumId: Int): Call<AlbumInfoDTO>

    @POST("/album/get/list")
    fun getByAlbumIdList(@Body albumIdList: List<Int>): Call<List<AlbumInfoDTO>>

    @GET("/album/get/user")
    fun getAlbumUserList(): Call<List<AlbumInfoDTO>>

    @GET("/album/add/{albumId}")
    fun addAlbumToUserList(@Path("albumId") albumId: Int): Call<AlbumInfoDTO>

    @GET("/album/remove/{albumId}")
    fun removeAlbumFromUserList(@Path("albumId") albumId: Int): Call<AlbumInfoDTO>

    @POST("/album/rate/{albumId}")
    fun rateAlbum(@Path("albumId") albumId: Int, @Body rateId: Int): Call<AlbumInfoDTO>
}
