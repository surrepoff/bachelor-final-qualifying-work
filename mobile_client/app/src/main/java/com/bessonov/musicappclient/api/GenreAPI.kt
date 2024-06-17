package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.GenreDTO
import retrofit2.Call
import retrofit2.http.GET

interface GenreAPI {
    @GET("/genre/get/all")
    fun getAll(): Call<List<GenreDTO>>
}