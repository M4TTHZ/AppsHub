package com.matheusramalho.appshub.ClimateWeather

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.matheusramalho.appshub.ClimateWeather.Api.Service.RetrofitClient
import com.matheusramalho.appshub.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ClimateWeatherActivity : AppCompatActivity() {

    private lateinit var etCidadeNome: TextInputEditText
    private lateinit var btnBuscarCidade: Button
    private lateinit var tvGraus: TextView
    private lateinit var tvVento: TextView
    private lateinit var tvClima: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvErro: TextView
    private lateinit var layoutResultados: LinearLayout
    private lateinit var tvInstrucao: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_climate_weather)

        inicializarViews()
        configurarListeners()
    }

    private fun inicializarViews() {
        etCidadeNome = findViewById(R.id.etCidadeNome)
        btnBuscarCidade = findViewById(R.id.btnBuscarCidade)
        tvGraus = findViewById(R.id.tvGraus)
        tvVento = findViewById(R.id.tvVento)
        tvClima = findViewById(R.id.tvClima)
        progressBar = findViewById(R.id.progressBar)
        tvErro = findViewById(R.id.tvErro)
        layoutResultados = findViewById(R.id.layoutResultados)
        tvInstrucao = findViewById(R.id.tvInstrucao)
    }

    private fun configurarListeners() {
        btnBuscarCidade.setOnClickListener {
            val nomeCidade = etCidadeNome.text.toString().trim()
            if (nomeCidade.isNotBlank()) {
                buscarCidade(nomeCidade)
                // Fechar teclado
                etCidadeNome.clearFocus()
            } else {
                Toast.makeText(this, "Digite o nome de uma cidade", Toast.LENGTH_SHORT).show()
            }
        }

        // Buscar ao pressionar Enter
        etCidadeNome.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                btnBuscarCidade.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun buscarCidade(nomeCidade: String) {
        lifecycleScope.launch {
            try {
                mostrarLoading(true)
                esconderErro()
                esconderResultados()

                // Buscar coordenadas da cidade
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiCidadeService.searchLocations(nomeCidade)
                }

                val location = response.results?.firstOrNull()
                if (location != null) {
                    buscarClima(location.latitude, location.longitude, location.name, location.country)
                } else {
                    mostrarErro("Cidade não encontrada: $nomeCidade")
                }

            } catch (e: IOException) {
                mostrarErro("Erro de conexão. Verifique sua internet.")
                e.printStackTrace()
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> mostrarErro("Requisição inválida")
                    404 -> mostrarErro("API não encontrada")
                    429 -> mostrarErro("Muitas requisições. Aguarde um momento.")
                    500 -> mostrarErro("Erro no servidor. Tente novamente.")
                    else -> mostrarErro("Erro HTTP: ${e.code()}")
                }
                e.printStackTrace()
            } catch (e: Exception) {
                mostrarErro("Erro inesperado: ${e.message}")
                e.printStackTrace()
            } finally {
                mostrarLoading(false)
            }
        }
    }

    private fun buscarClima(latitude: Double, longitude: Double, cidadeNome: String?, pais: String?) {
        lifecycleScope.launch {
            try {
                mostrarLoading(true)

                // Buscar clima
                val weatherResponse = withContext(Dispatchers.IO) {
                    RetrofitClient.weatherService.getCurrentWeather(latitude, longitude)
                }

                val currentWeather = weatherResponse.current_weather

                withContext(Dispatchers.Main) {
                    // Formatar temperatura
                    val temperatura = currentWeather.temperature
                    val tempFormatada = if (temperatura == temperatura.toInt().toDouble()) {
                        "${temperatura.toInt()}°C"
                    } else {
                        String.format("%.1f°C", temperatura)
                    }

                    // Exibir resultados
                    tvGraus.text = tempFormatada
                    tvVento.text = String.format("💨 Vento: %.1f km/h", currentWeather.windspeed)
                    tvClima.text = getWeatherDescription(currentWeather.weathercode)

                    // Mostrar resultados e esconder instrução
                    mostrarResultados()

                    // Opcional: mostrar nome da cidade no título
                    if (!cidadeNome.isNullOrBlank()) {
                        supportActionBar?.title = if (!pais.isNullOrBlank()) {
                            "$cidadeNome - $pais"
                        } else {
                            cidadeNome
                        }
                    }
                }

            } catch (e: IOException) {
                mostrarErro("Erro de conexão ao buscar clima")
                e.printStackTrace()
            } catch (e: HttpException) {
                mostrarErro("Erro ao buscar clima: ${e.code()}")
                e.printStackTrace()
            } catch (e: Exception) {
                mostrarErro("Erro ao buscar clima: ${e.message}")
                e.printStackTrace()
            } finally {
                mostrarLoading(false)
            }
        }
    }

    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "☀️ Céu limpo"
            1 -> "🌤️ Principalmente limpo"
            2 -> "⛅ Parcialmente nublado"
            3 -> "☁️ Nublado"
            45, 48 -> "🌫️ Nevoeiro"
            51, 53, 55 -> "🌧️ Garoa"
            56, 57 -> "❄️ Garoa congelante"
            61, 63, 65 -> "🌧️ Chuva"
            66, 67 -> "❄️ Chuva congelante"
            71, 73, 75 -> "🌨️ Neve"
            77 -> "🌨️ Grãos de neve"
            80, 81, 82 -> "☔ Pancadas de chuva"
            85, 86 -> "🌨️ Pancadas de neve"
            95 -> "⛈️ Trovoadas"
            96, 99 -> "⛈️ Trovoadas com granizo"
            else -> "🌡️ Clima desconhecido"
        }
    }

    private fun mostrarLoading(mostrar: Boolean) {
        progressBar.visibility = if (mostrar) View.VISIBLE else View.GONE
        btnBuscarCidade.isEnabled = !mostrar
    }

    private fun mostrarErro(mensagem: String) {
        tvErro.text = mensagem
        tvErro.visibility = View.VISIBLE
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

        // Esconder erro após 5 segundos
        tvErro.postDelayed({
            esconderErro()
        }, 5000)
    }

    private fun esconderErro() {
        tvErro.visibility = View.GONE
    }

    private fun mostrarResultados() {
        layoutResultados.visibility = View.VISIBLE
        tvInstrucao.visibility = View.GONE
    }

    private fun esconderResultados() {
        layoutResultados.visibility = View.GONE
        tvInstrucao.visibility = View.VISIBLE
    }
}