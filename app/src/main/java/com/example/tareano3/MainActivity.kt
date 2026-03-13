package com.example.tareano3

import android.content.Intent
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        // Usamos la IP de tu computadora que configuramos antes
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.76:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                tvMessage.text = "Llena ambos campos"
                tvMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }

            tvMessage.text = "Iniciando sesión..."
            tvMessage.setTextColor(Color.GRAY)

            // Empaquetamos la petición de Login
            val request = LoginRequest(user, pass)

            apiService.loginUser(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        // ¡LOGIN EXITOSO!
                        // Creamos el Intent para viajar a WelcomeActivity
                        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                        // Le metemos el nombre de usuario a la maleta del viaje
                        intent.putExtra("USERNAME", user)
                        // Arrancamos el viaje
                        startActivity(intent)

                        // Limpiamos el mensaje por si el usuario regresa atrás
                        tvMessage.text = ""
                    } else {
                        // LOGIN FALLIDO (contraseña incorrecta o usuario no existe)
                        tvMessage.text = "Error: Credenciales incorrectas"
                        tvMessage.setTextColor(Color.RED)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    tvMessage.text = "Error de conexión: ${t.message}"
                    tvMessage.setTextColor(Color.RED)
                }
            })
        }

        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        btnGoToRegister.setOnClickListener {
            // Viajamos a la pantalla de Registro
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}