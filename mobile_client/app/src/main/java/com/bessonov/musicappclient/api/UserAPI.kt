package com.bessonov.musicappclient.api

import com.bessonov.musicappclient.dto.UserDataDTO
import com.bessonov.musicappclient.dto.UserEditRequestDTO
import com.bessonov.musicappclient.dto.UserEditResponseDTO
import com.bessonov.musicappclient.dto.UserLoginDTO
import com.bessonov.musicappclient.dto.UserRegisterDTO
import com.bessonov.musicappclient.dto.UserResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @GET("/user/info")
    fun info(): Call<UserDataDTO>

    @POST("/user/login")
    fun login(@Body userLoginDTO: UserLoginDTO): Call<UserResponseDTO>

    @POST("/user/register")
    fun register(@Body userRegisterDTO: UserRegisterDTO): Call<UserResponseDTO>

    @POST("/user/edit")
    fun edit(@Body userEditRequestDTO: UserEditRequestDTO): Call<UserEditResponseDTO>
}

