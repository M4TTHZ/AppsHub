package com.matheusramalho.appshub.Sharedpreferences

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.matheusramalho.appshub.R

class SharedPreferencesActivity2 : AppCompatActivity() {
    private lateinit var textView: TextView

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences2)

        inicializarViews()
        exibirApelido()
        buttonVoltar()
    }
    private fun inicializarViews() {
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
    }
    private fun exibirApelido() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val apelido = sharedPreferences.getString("apelido", "Nenhum apelido encontrado")
        textView.text = "Apelido: $apelido"
    }
    private fun buttonVoltar() {
        button.setOnClickListener {
            val intent = Intent(this, SharedPreferencesActivity::class.java)

            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            Toast.makeText(this, "Preferências limpas", Toast.LENGTH_SHORT).show()

            startActivity(intent)
        }
    }

}