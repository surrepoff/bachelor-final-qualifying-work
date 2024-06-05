package com.bessonov.musicappclient.api

import android.content.Context
import com.bessonov.musicappclient.utils.ConfigManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private lateinit var baseUrl: String
    private lateinit var retrofit: Retrofit
    private lateinit var configManager: ConfigManager

    fun getRetrofit(context: Context) : Retrofit {
        if (!::retrofit.isInitialized) {
            configManager = ConfigManager(context)
            baseUrl = configManager.getServerIp()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
        }
        return retrofit
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}