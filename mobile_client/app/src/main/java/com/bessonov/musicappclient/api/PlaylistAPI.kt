package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistAPI {
    @GET("/playlist/get/all")
    fun getAll(): Call<List<PlaylistInfoDTO>>

    @GET("/playlist/get/{playlistId}")
    fun getByPlaylistId(@Path("playlistId") playlistId: Int): Call<PlaylistInfoDTO>

    @POST("/playlist/get/list")
    fun getByPlaylistIdList(@Body playlistIdList: List<Int>): Call<List<PlaylistInfoDTO>>

    @GET("/playlist/get/user")
    fun getPlaylistUserList(): Call<List<PlaylistInfoDTO>>
}