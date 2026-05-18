package com.matheusramalho.appshub.SQLite.Model

data class Produto(
    var id: Int = 0,
    var nome: String,
    var quantidade: Int,
    var preco: Double
)