package com.androidatc.kualifarma

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Forzar Modo Claro globalmente antes de cargar la vista
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUser)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        cbRemember?.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "activada" else "desactivada"
            Toast.makeText(this, "Opción 'Recordar usuario' $status", Toast.LENGTH_SHORT).show()
        }

        btnLogin?.setOnClickListener {
            val user = etUser.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                Toast.makeText(this, "¡Bienvenido a kuali Farma!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }
}