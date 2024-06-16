package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.PlaylistEditDTO
import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.UserPlaylistDTO
import com.bessonov.musicappclient.dto.UserRatingDTO
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

    @GET("/playlist/add/{playlistId}")
    fun addPlaylistToUserList(@Path("playlistId") playlistId: Int): Call<UserPlaylistDTO>

    @GET("/playlist/remove/{playlistId}")
    fun removePlaylistFromUserList(@Path("playlistId") playlistId: Int): Call<UserPlaylistDTO>

    @POST("/playlist/rate/{playlistId}")
    fun ratePlaylist(@Path("playlistId") playlistId: Int, @Body rateId: Int): Call<UserRatingDTO>

    @POST("/playlist/create")
    fun createPlaylist(@Body playlistEditDTO: PlaylistEditDTO): Call<PlaylistInfoDTO>

    @GET("/playlist/delete/{artistId}")
    fun deletePlaylist(@Path("playlistId") playlistId: Int): Call<PlaylistInfoDTO>

    @POST("/playlist/edit/{playlistId}")
    fun editPlaylist(
        @Path("playlistId") playlistId: Int,
        @Body playlistEditDTO: PlaylistEditDTO
    ): Call<PlaylistInfoDTO>

    @POST("/playlist/edit/{playlistId}/rename")
    fun editPlaylistRename(
        @Path("playlistId") playlistId: Int,
        @Body newPlaylistName: String
    ): Call<PlaylistInfoDTO>

    @GET("/playlist/edit/{playlistId}/track/add/{trackId]")
    fun editPlaylistAddTrack(
        @Path("playlistId") playlistId: Int,
        @Path("trackId") trackId: Int
    ): Call<PlaylistInfoDTO>

    @GET("/playlist/edit/{playlistId}/track/remove/{trackId]")
    fun editPlaylistRemoveTrack(
        @Path("playlistId") playlistId: Int,
        @Path("trackId") trackId: Int
    ): Call<PlaylistInfoDTO>

    @GET("/playlist/edit/{playlistId}/accessLevel/{userId]")
    fun editPlaylistUserAccessLevel(
        @Path("playlistId") playlistId: Int,
        @Path("userId") userId: Int,
        @Body newAccessLevelId: Int
    ): Call<PlaylistInfoDTO>
}