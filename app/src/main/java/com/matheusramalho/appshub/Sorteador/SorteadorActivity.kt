package com.matheusramalho.appshub.Sorteador

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.matheusramalho.appshub.R
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SorteadorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorteador)
    }

    fun sortear(view: View) {

        val textResultado = findViewById<TextView>(R.id.textview_resultado)
        val numero = Random.nextInt(101)

        textResultado.setText("Número sorteado: $numero")
    }

}