package com.matheusramalho.appshub.SQLite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matheusramalho.appshub.SQLite.Adapter.ProdutoAdapter
import com.matheusramalho.appshub.SQLite.Database.ProdutoDAO
import com.matheusramalho.appshub.SQLite.Model.Produto
import com.matheusramalho.appshub.R

class SQLiteActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtQuantidade: EditText
    private lateinit var edtPreco: EditText
    private lateinit var btnSalvar: Button
    private lateinit var recycler: RecyclerView

    private lateinit var dao: ProdutoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        dao = ProdutoDAO(this)

        edtNome = findViewById(R.id.edtNome)
        edtQuantidade = findViewById(R.id.edtQuantidade)
        edtPreco = findViewById(R.id.edtPreco)
        btnSalvar = findViewById(R.id.btnSalvar)
        recycler = findViewById(R.id.recyclerProdutos)

        recycler.layoutManager =
            LinearLayoutManager(this)

        carregarProdutos()

        btnSalvar.setOnClickListener {

            val produto = Produto(
                nome = edtNome.text.toString(),
                quantidade = edtQuantidade.text.toString().toInt(),
                preco = edtPreco.text.toString().toDouble()
            )

            dao.inserir(produto)

            edtNome.text.clear()
            edtQuantidade.text.clear()
            edtPreco.text.clear()

            carregarProdutos()
        }
    }

    private fun carregarProdutos() {

        val lista = dao.listar()

        recycler.adapter =
            ProdutoAdapter(lista)
    }
}