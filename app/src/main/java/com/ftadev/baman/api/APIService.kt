package com.ftadev.baman.api

import com.ftadev.baman.model.Page
import com.ftadev.baman.model.SampleItem
import com.ftadev.baman.model.SampleList
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIService {

    @POST("challenge/list")
    fun getSampleList(@Body page: Page): Single<SampleList>

    @GET("challenge/getbyid")
    fun getSampleItem(@Query("page") id: String): Single<SampleItem>

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