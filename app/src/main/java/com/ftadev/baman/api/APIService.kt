package com.ftadev.baman.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface APIService {

    companion object {
        val instance: APIService by lazy {

            val client = OkHttpClient.Builder().build()

            Retrofit.Builder()
                .baseUrl("https://dev.api.baman.club/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(APIService::class.java)
        }
    }

}