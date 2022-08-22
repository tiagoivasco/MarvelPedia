package com.ivasco.marvelpedia.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelRepository {
    fun makeRequest(): MarvelApi {
        val baseUrl = "https://developer.marvel.com/"

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MarvelApi::class.java)
    }
}