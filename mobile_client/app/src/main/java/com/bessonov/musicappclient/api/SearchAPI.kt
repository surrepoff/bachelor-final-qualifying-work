package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.SearchInfoDTO
import com.bessonov.musicappclient.dto.SearchRequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchAPI {
    @POST("/search/name")
    fun searchByName(@Body name: SearchRequestDTO): Call<SearchInfoDTO>
}