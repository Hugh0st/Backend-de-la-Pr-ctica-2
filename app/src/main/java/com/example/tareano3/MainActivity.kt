package com.example.tareano3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Enlazamos los elementos visuales de tu XML
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        // 2. Configuramos Retrofit con la IP de tu compu
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.76:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // 3. Configuramos qué pasa al hacer clic en el botón
        btnRegister.setOnClickListener {
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            // Verificamos que no envíen campos vacíos
            if (user.isEmpty() || pass.isEmpty()) {
                tvMessage.text = "Por favor, llena ambos campos"
                tvMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }

            tvMessage.text = "Registrando..."
            tvMessage.setTextColor(Color.GRAY)

            // Empaquetamos los datos
            val request = RegisterRequest(user, pass)

            // Hacemos la petición POST a /register
            apiService.registerUser(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        // Caso exitoso (Código 200 o 201)
                        tvMessage.text = response.body()?.message ?: "Registro exitoso"
                        tvMessage.setTextColor(Color.rgb(0, 150, 0)) // Verde oscuro
                    } else {
                        // Caso de error (ej. código 400 porque el usuario ya existe)
                        tvMessage.text = "Error: El usuario ya existe"
                        tvMessage.setTextColor(Color.RED)
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    // Error de red (Docker apagado, cambio de IP, etc.)
                    tvMessage.text = "Error de conexión: ${t.message}"
                    tvMessage.setTextColor(Color.RED)
                }
            })
        }
    }
}