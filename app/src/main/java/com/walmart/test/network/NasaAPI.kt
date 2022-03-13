package com.walmart.test.network

import com.walmart.test.model.NasaResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NasaAPI {

    @GET("planetary/apod")
    fun getAstronomyPicture(@Query("api_key") apiKey : String): Deferred<NasaResponse>
}