package com.example.latestearthquakes.data.service

import com.example.latestearthquakes.data.model.DepremModel
import retrofit2.Call
import retrofit2.http.GET

interface DepremlerAPIService {
    @GET("#")
    fun getEarthquakes(): Call<DepremModel>
}