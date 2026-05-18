package com.matheusramalho.appshub.SQLite.Database


import android.content.ContentValues
import android.content.Context
import com.matheusramalho.appshub.SQLite.Model.Produto
/*
 A classe ProdutoDAO é responsável por realizar as operações de CRUD (Create, Read, Update, Delete) no banco de dados SQLite.
Ela utiliza a classe DatabaseHelper para obter acesso ao banco de dados e executar as operações necessárias.
*/
class ProdutoDAO(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // CREATE
    fun inserir(produto: Produto): Long {

        val db = dbHelper.writableDatabase // O método writableDatabase é usado para obter uma instância do banco de dados que pode ser escrita, ou seja, onde podemos inserir, atualizar ou deletar dados.

        val values = ContentValues().apply {

            put(DatabaseHelper.COLUMN_NOME, produto.nome)
            put(DatabaseHelper.COLUMN_QUANTIDADE, produto.quantidade)
            put(DatabaseHelper.COLUMN_PRECO, produto.preco)
        }

        return db.insert(
            DatabaseHelper.TABLE_PRODUTOS,
            null,
            values
        )
    }

    // READ
    fun listar(): List<Produto> {
        //mutableListOf é uma função que cria uma lista mutável, ou seja, uma lista que pode ser modificada após a sua criação. Ela é parte da biblioteca padrão do Kotlin e é usada para criar listas que podem ser alteradas dinamicamente, permitindo adicionar, remover ou modificar elementos conforme necessário.
        val lista = mutableListOf<Produto>()
        // O método readableDatabase é usado para obter uma instância do banco de dados que pode ser lida, ou seja, onde podemos consultar os dados. Ele é utilizado quando queremos apenas ler os dados do banco de dados, sem a intenção de modificá-los.
        val db = dbHelper.readableDatabase
        // cursor é um objeto que representa o resultado de uma consulta ao banco de dados. Ele é usado para iterar sobre os resultados da consulta e acessar os dados retornados. O método rawQuery é usado para executar uma consulta SQL diretamente, e ele retorna um Cursor que pode ser usado para navegar pelos resultados da consulta.
        val cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_PRODUTOS}",
            null
        )
        // O método moveToFirst é usado para mover o cursor para a primeira linha do resultado da consulta. Ele retorna true se houver pelo menos uma linha no resultado, e false caso contrário. Se o cursor estiver vazio, ou seja, se a consulta não retornar nenhum resultado, moveToFirst retornará false, indicando que não há dados para processar.
        if (cursor.moveToFirst()) {

            do {

                val produto = Produto(
                    id = cursor.getInt(0),
                    nome = cursor.getString(1),
                    quantidade = cursor.getInt(2),
                    preco = cursor.getDouble(3)
                )

                lista.add(produto)

            } while (cursor.moveToNext())
        }

        cursor.close()

        return lista
    }

    // UPDATE
    fun atualizar(produto: Produto): Int {

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {

            put(DatabaseHelper.COLUMN_NOME, produto.nome)
            put(DatabaseHelper.COLUMN_QUANTIDADE, produto.quantidade)
            put(DatabaseHelper.COLUMN_PRECO, produto.preco)
        }

        return db.update(
            DatabaseHelper.TABLE_PRODUTOS,
            values,
            "${DatabaseHelper.COLUMN_ID}=?",
            arrayOf(produto.id.toString())
        )
    }

    // DELETE
    fun deletar(id: Int): Int {

        val db = dbHelper.writableDatabase

        return db.delete(
            DatabaseHelper.TABLE_PRODUTOS,
            "${DatabaseHelper.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }
}