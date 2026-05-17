package com.matheusramalho.appshub

// =============================================================================
// RecyclerViewActivity.kt   (Aula 09 - RecyclerView)
// -----------------------------------------------------------------------------
// RecyclerView com layout custom, adapter próprio e clique em item.
// Mostra uma "agenda" fictícia. Compare com a aula 08 (ListView) pra ver a
// diferença.
// =============================================================================

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java
import com.matheusramalho.appshub.Sorteador.SorteadorActivity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerApps: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerApps = findViewById(R.id.recyclerApps)

        // Lista mock de apps.
        val apps = listOf(
            App("Sorteador", "KOTLIN"),
            App("Cep Search", "KOTLIN"),
            App("To-do List", "KOTLIN"),
            App("Climateweather", "KOTLIN"),
            App("Sharedpreferences", "KOTLIN")
        )

        // Adapter recebe a lista e o callback de clique.
        val adapter = AppAdapter(apps) { app ->
            when (app.name) {
                "Sorteador" -> {
                    val intent = Intent(this, SorteadorActivity::class.java)
                    startActivity(intent)
                }
                "Cep Search" -> {
//                    val intent = Intent(this, CepSearchActivity::class.java)
//                    startActivity(intent)
                }
                "To-do List" -> {
//                    val intent = Intent(this, ToDoListActivity::class.java)
//                    startActivity(intent)
                }
                "Climateweather" -> {
//                    val intent = Intent(this, ClimateWeatherActivity::class.java)
//                    startActivity(intent)
                }
                "Sharedpreferences" -> {
//                    val intent = Intent(this, SharedPreferencesActivity::class.java)
//                    startActivity(intent)
                }
            }
        }

        // LayoutManager: define orientação. Outras opções:
        //   - LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //   - GridLayoutManager(this, 2)              // grade de 2 colunas
        //   - StaggeredGridLayoutManager(2, ...)      // estilo Pinterest
        recyclerApps.layoutManager = LinearLayoutManager(this)
        recyclerApps.adapter = adapter
    }
}
