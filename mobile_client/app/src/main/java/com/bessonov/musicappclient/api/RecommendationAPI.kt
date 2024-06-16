package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.RecommendationInfoDTO
import retrofit2.Call
import retrofit2.http.GET

interface RecommendationAPI {
    @GET("/recommendation/get/user")
    fun getUserRecommendationList(): Call<List<RecommendationInfoDTO>>

}