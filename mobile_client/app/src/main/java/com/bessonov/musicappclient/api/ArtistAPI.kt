package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.ArtistInfoDTO
import com.bessonov.musicappclient.dto.UserArtistDTO
import com.bessonov.musicappclient.dto.UserRatingDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArtistAPI {
    @GET("/artist/get/all")
    fun getAll(): Call<List<ArtistInfoDTO>>

    @GET("/artist/get/{artistId}")
    fun getByArtistId(@Path("artistId") artistId: Int): Call<ArtistInfoDTO>

    @POST("/artist/get/list")
    fun getByArtistIdList(@Body artistIdList: List<Int>): Call<List<ArtistInfoDTO>>

    @GET("/artist/get/user")
    fun getArtistUserList(): Call<List<ArtistInfoDTO>>

    @GET("/artist/add/{artistId}")
    fun addArtistToUserList(@Path("artistId") artistId: Int): Call<ArtistInfoDTO>

    @GET("/artist/remove/{artistId}")
    fun removeArtistFromUserList(@Path("artistId") artistId: Int): Call<ArtistInfoDTO>

    @POST("/artist/rate/{artistId}")
    fun rateArtist(@Path("artistId") artistId: Int, @Body rateId: Int): Call<ArtistInfoDTO>
}
