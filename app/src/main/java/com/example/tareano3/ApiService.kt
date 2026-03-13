package com.example.tareano3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/")
    fun getStatus(): Call<ApiResponse>

    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    // LA NUEVA RUTA PARA EL LOGIN
    @POST("/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}