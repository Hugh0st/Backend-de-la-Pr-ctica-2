package com.example.tareano3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Enlazamos el texto gigante de bienvenida
        val tvWelcomeMessage = findViewById<TextView>(R.id.tvWelcomeMessage)

        // Recibimos el nombre de usuario que nos va a mandar el Login
        // Si por alguna razón llega vacío, dirá "Usuario"
        val username = intent.getStringExtra("USERNAME") ?: "Usuario"

        // Actualizamos el texto en pantalla
        tvWelcomeMessage.text = "¡Bienvenido, $username!"
    }
}