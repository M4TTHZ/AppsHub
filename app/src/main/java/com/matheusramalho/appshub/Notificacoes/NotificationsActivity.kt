package com.matheusramalho.appshub.Notificacoes

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.matheusramalho.appshub.R

class NotificationsActivity : AppCompatActivity() {

    private val channelId = "canal_notificacao" // ID do canal de notificação

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        criarCanalNotificacao()

        val btnNotificacao = findViewById<Button>(R.id.btnNotificacao)

        btnNotificacao.setOnClickListener {
            verificarPermissao()
        }
    }

    private fun verificarPermissao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {

                enviarNotificacao()
            } else {

                solicitarPermissao.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

        } else {

            enviarNotificacao()
        }
    }

    private val solicitarPermissao = registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { permitido ->

            if (permitido) {

                enviarNotificacao()
            } else {

                Toast.makeText(
                    this,
                    "Permissão negada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun criarCanalNotificacao() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val nome = "Canal Principal"

            val descricao = "Canal de notificações do app"

            val importancia = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(
                channelId,
                nome,
                importancia
            )

            channel.description = descricao

            val notificationManager = getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enviarNotificacao() {

        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Nova mensagem")
                .setContentText("Você recebeu uma notificação!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }
}