package com.example.tareano3

import android.graphics.Color
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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Solo dejamos la línea limpia que carga tu diseño
        setContentView(R.layout.activity_register)

        // 1. Enlazamos los elementos usando los IDs de tu XML de Registro
        val etUsername = findViewById<EditText>(R.id.etRegUsername)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvMessage = findViewById<TextView>(R.id.tvRegMessage)

        // 2. Configuramos Retrofit para el registro
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.76:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // 3. Lógica del botón de registrar
        btnRegister.setOnClickListener {
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                tvMessage.text = "Por favor, llena ambos campos"
                tvMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }

            tvMessage.text = "Registrando..."
            tvMessage.setTextColor(Color.GRAY)

            val request = RegisterRequest(user, pass)

            apiService.registerUser(request).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        tvMessage.text = response.body()?.message ?: "Registro exitoso"
                        tvMessage.setTextColor(Color.rgb(0, 150, 0)) // Verde
                    } else {
                        tvMessage.text = "Error: El usuario ya existe"
                        tvMessage.setTextColor(Color.RED)
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    tvMessage.text = "Error de conexión: ${t.message}"
                    tvMessage.setTextColor(Color.RED)
                }
            })
        }
    }
}