package com.example.latestearthquakes.data.service

import com.example.latestearthquakes.data.model.DepremModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DepremlerAPIService {
    @GET("#")
    fun getEarthquakes(): Call<DepremModel>

    @GET("#")
    fun getEarthquakeForSize(
        @Query("size") size:Int? = 10
    ): Call<DepremModel>
}
