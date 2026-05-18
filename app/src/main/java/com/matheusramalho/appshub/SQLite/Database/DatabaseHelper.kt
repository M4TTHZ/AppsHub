package com.matheusramalho.appshub.SQLite.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
// (SQLiteOpenHelper) é uma classe que ajuda a gerenciar a criação e atualização do banco de dados SQLite.
// Ela fornece métodos para criar, atualizar e abrir o banco de dados, além de lidar com a versão do banco de dados.
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) { // O construtor da classe DatabaseHelper chama o construtor da classe SQLiteOpenHelper, passando o contexto, o nome do banco de dados, um cursor factory (nulo neste caso) e a versão do banco de dados.

    companion object {
        private const val DATABASE_NAME = "estoque.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_PRODUTOS = "produtos"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_QUANTIDADE = "quantidade"
        const val COLUMN_PRECO = "preco"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_PRODUTOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT,
                $COLUMN_QUANTIDADE INTEGER,
                $COLUMN_PRECO REAL
            )
        """

        db.execSQL(createTable)
    }

    override fun onUpgrade( // O método onUpgrade é chamado quando a versão do banco de dados é incrementada. Ele é responsável por atualizar o esquema do banco de dados, se necessário. Neste exemplo, ele simplesmente descarta a tabela existente e chama onCreate para recriá-la.
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUTOS")

        onCreate(db)
    }
}