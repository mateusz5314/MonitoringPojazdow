package com.example.lokalizator.networkApi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Client {
    fun getClient(): Service {
        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
                .baseUrl("https://en9jeh4m4w51t8f.m.pipedream.net")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder
                .client(httpClient.build())
                .build()

        return retrofit.create(Service::class.java)
    }
}