package com.example.tareano3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // La que ya tenías (Ejercicio 1)
    @GET("/")
    fun getStatus(): Call<ApiResponse>

    // La NUEVA para el Ejercicio 2
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}