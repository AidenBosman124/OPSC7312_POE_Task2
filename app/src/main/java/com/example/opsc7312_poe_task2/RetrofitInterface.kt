package com.example.opsc7312_poe_task2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("your_api_endpoint") // Replace with the actual API endpoint
    fun getHotspots(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("dist") distance: Int,
        @Query("fmt") format: String = "json"
    ): Call<List<Hotspot>>
}