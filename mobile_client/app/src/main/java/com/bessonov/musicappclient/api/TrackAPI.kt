package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.TrackInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TrackAPI {
    @GET("/track/get/all")
    fun getAll(): Call<List<TrackInfoDTO>>

    @GET("/track/get/{trackId}")
    fun getByTrackId(@Path("trackId") trackId: Int): Call<TrackInfoDTO>

    @POST("/track/get/list")
    fun getByTrackIdList(@Body trackIdList: List<Int>): Call<List<TrackInfoDTO>>

    @GET("/track/get/user")
    fun getAllUser(): Call<List<TrackInfoDTO>>
}
