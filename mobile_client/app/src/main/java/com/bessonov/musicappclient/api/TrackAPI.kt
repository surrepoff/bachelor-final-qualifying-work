package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.TrackInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TrackAPI {
    @GET("/track/get/all")
    fun getAll(): Call<List<TrackInfoDTO>>

    @POST("/track/get/byTrackId")
    fun getByTrackId(@Body trackId: Int): Call<TrackInfoDTO>

    @POST("/track/get/byTrackId/list")
    fun getByTrackIdList(@Body trackIdList: List<Int>): Call<List<TrackInfoDTO>>
}
