package com.matheusramalho.appshub.Sharedpreferences

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.matheusramalho.appshub.R

class SharedPreferencesActivity : AppCompatActivity() {
    private lateinit var edApelido: TextInputEditText
    private lateinit var cbNperguntar: CheckBox
    private lateinit var btProsseguir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        inicializarViews()
        buttonProsseguir()
        naoPerguntar()

    }
    private fun inicializarViews() {
        edApelido = findViewById(R.id.edApelido)
        cbNperguntar = findViewById(R.id.cbNperguntar)
        btProsseguir = findViewById(R.id.btProsseguir)
    }
    private fun preferenciasDoUsuario() {
        val apelido = edApelido.text.toString().trim()
        val naoPerguntar = cbNperguntar.isChecked

        // Salvar os dados usando SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("apelido", apelido)
        editor.putBoolean("naoPerguntar", naoPerguntar)
        editor.apply()
    }
    private fun buttonProsseguir() {
        btProsseguir.setOnClickListener {
            preferenciasDoUsuario()
            if (edApelido != null) {
                val intent = Intent(this, SharedPreferencesActivity2::class.java)
                startActivity(intent)
            } else{
                edApelido.error = "Insira um apelido"
            }
        }
    }
    private fun naoPerguntar() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val naoPerguntar = sharedPreferences.getBoolean("naoPerguntar", false)

        if (naoPerguntar) {
            val intent = Intent(this, SharedPreferencesActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}