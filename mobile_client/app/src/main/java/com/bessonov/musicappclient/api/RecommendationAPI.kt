package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.PlaylistInfoDTO
import com.bessonov.musicappclient.dto.RecommendationCreateDTO
import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecommendationAPI {
    @GET("/recommendation/get/{userRecommendationId}")
    fun getByUserRecommendationId(@Path("userRecommendationId") userRecommendationId: Int): Call<RecommendationInfoDTO>

    @GET("/recommendation/get/user")
    fun getUserRecommendationList(): Call<List<RecommendationInfoDTO>>

    @POST("/recommendation/rate/{userRecommendationId}")
    fun rateUserRecommendation(
        @Path("userRecommendationId") userRecommendationId: Int,
        @Body rateId: Int
    ): Call<RecommendationInfoDTO>

    @POST("/recommendation/create")
    fun createUserRecommendation(@Body recommendationCreateDTO: RecommendationCreateDTO): Call<RecommendationInfoDTO>

    @GET("/recommendation/delete/{userRecommendationId}")
    fun deleteUserRecommendation(@Path("userRecommendationId") userRecommendationId: Int): Call<RecommendationInfoDTO>

    @GET("/recommendation/add/{userRecommendationId}")
    fun addUserRecommendationAsPlaylist(@Path("userRecommendationId") userRecommendationId: Int): Call<PlaylistInfoDTO>

    @GET("/recommendation/update/status/{extractionTypeId}")
    fun getUpdateUserNeuralNetworkStatus(@Path("extractionTypeId") extractionTypeId: Int): Call<Boolean>

    @GET("/recommendation/update/{extractionTypeId}")
    fun updateUserNeuralNetwork(@Path("extractionTypeId") extractionTypeId: Int): Call<Boolean>
}