package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.ResponseDTO
import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.dto.UserLoginDTO
import com.bessonov.musicappclient.dto.UserRegisterDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @GET("/user/info")
    fun info(): Call<UserDataDTO>

    @POST("/user/login")
    fun login(@Body userLoginDTO: UserLoginDTO): Call<ResponseDTO>

    @POST("/user/register")
    fun register(@Body userRegisterDTO: UserRegisterDTO): Call<ResponseDTO>
}

