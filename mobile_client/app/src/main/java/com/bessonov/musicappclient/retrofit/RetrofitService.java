package com.bessonov.musicappclient.retrofit;

import com.bessonov.musicappclient.utils.ServerPropertiesReader;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        String url = "http://" + ServerPropertiesReader.INSTANCE.getProperty("server.ip") +
                ":" +ServerPropertiesReader.INSTANCE.getProperty("server.port");
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
