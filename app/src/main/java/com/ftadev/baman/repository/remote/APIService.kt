package com.ftadev.baman.repository.remote

import androidx.lifecycle.LiveData
import com.ftadev.baman.BASE_URL
import com.ftadev.baman.repository.model.Page
import com.ftadev.baman.repository.model.SampleItem
import com.ftadev.baman.repository.model.SampleList
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIService {

    @POST("challenge/list")
    fun getSampleList(@Body page: Page): LiveData<ApiResponse<SampleList>>

    @GET("challenge/getbyid")
    fun getSampleItem(@Query("id") id: String): Single<SampleItem>

    companion object {
        val instance: APIService by lazy {

            val client = OkHttpClient.Builder().build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(APIService::class.java)
        }
    }

}