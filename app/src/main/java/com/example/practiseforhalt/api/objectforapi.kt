package com.example.practiseforhalt.api
import com.example.practiseforhalt.authinterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object objectforapi {
    private const val BASE_URL = "http://172.22.38.179:4500"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: api = retrofit.create(api::class.java)
    val apiServices:noteapi= retrofit.create(noteapi::class.java)
}
