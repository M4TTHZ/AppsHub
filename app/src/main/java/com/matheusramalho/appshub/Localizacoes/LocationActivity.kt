package com.matheusramalho.appshub.Localizacoes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.matheusramalho.appshub.R

class LocationActivity : AppCompatActivity() {

    private lateinit var txtLocalizacao: TextView
    private lateinit var btnLocalizacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        txtLocalizacao = findViewById(R.id.txtLocalizacao)
        btnLocalizacao = findViewById(R.id.btnLocalizacao)

        btnLocalizacao.setOnClickListener {
            verificarPermissao()
        }
    }

    private fun verificarPermissao() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pegarLocalizacao()
        } else {
            solicitarPermissao.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val solicitarPermissao = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
        ) { permitido ->

            if (permitido) {
                pegarLocalizacao()
            } else {

                Toast.makeText(
                    this,
                    "Permissão negada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun pegarLocalizacao() {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            location ->
                if (location != null) {

                    val latitude = location.latitude
                    val longitude = location.longitude

                    txtLocalizacao.text = "Latitude: $latitude\nLongitude: $longitude"
                } else {
                    txtLocalizacao.text = "Não foi possível obter localização"
                }
            }
    }
}