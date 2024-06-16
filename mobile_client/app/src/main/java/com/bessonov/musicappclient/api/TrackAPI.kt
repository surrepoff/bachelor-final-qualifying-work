package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.TrackInfoDTO
import com.bessonov.musicappclient.dto.UserRatingDTO
import com.bessonov.musicappclient.dto.UserTrackDTO
import okhttp3.ResponseBody
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
    fun getTrackUserList(): Call<List<TrackInfoDTO>>

    @GET("/track/get/history")
    fun getTrackHistoryList(): Call<List<TrackInfoDTO>>

    @GET("/track/add/{trackId}")
    fun addTrackToUserList(@Path("trackId") trackId: Int): Call<UserTrackDTO>

    @GET("/track/add/history/{trackId}")
    fun addTrackToHistoryList(@Path("trackId") trackId: Int): Call<UserTrackDTO>

    @GET("/track/remove/{trackId}")
    fun removeTrackFromUserList(@Path("trackId") trackId: Int): Call<UserTrackDTO>

    @POST("/track/rate/{trackId}")
    fun rateTrack(@Path("trackId") trackId: Int, @Body rateId: Int): Call<UserRatingDTO>

    @GET("/track/stream/{trackId}")
    fun streamAudio(@Path("trackId") trackId: Int): Call<ResponseBody>

    @GET("/track/download/{trackId}")
    fun downloadFile(@Path("trackId") trackId: Int): Call<ResponseBody>
}
