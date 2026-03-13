package com.example.tareano3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazamos la vista
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        // 1. Configuramos Retrofit con la IP del emulador
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // 2. Hacemos la petición en segundo plano
        apiService.getStatus().enqueue(object : Callback<ApiResponse> {

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // Si todo sale bien, extraemos el mensaje y lo mostramos
                    val apiResponse = response.body()
                    tvMessage.text = apiResponse?.message ?: "Respuesta vacía"
                } else {
                    tvMessage.text = "Error del servidor: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Si falla la red, el emulador no tiene internet o Docker está apagado
                tvMessage.text = "Error de conexión: ${t.message}"
            }
        })
    }
}