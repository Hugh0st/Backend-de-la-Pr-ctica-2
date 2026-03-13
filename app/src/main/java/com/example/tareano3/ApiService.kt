package com.example.tareano3

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    fun getStatus(): Call<ApiResponse>
}