package com.matheusramalho.appshub.SQLite.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matheusramalho.appshub.SQLite.Model.Produto
import com.matheusramalho.appshub.R

class ProdutoAdapter(
    private val lista: List<Produto>
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    class ProdutoViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val txtNome: TextView =
            view.findViewById(R.id.txtNome)

        val txtQuantidade: TextView =
            view.findViewById(R.id.txtQuantidade)

        val txtPreco: TextView =
            view.findViewById(R.id.txtPreco)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdutoViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_produto_sqlite,
                parent,
                false
            )

        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProdutoViewHolder,
        position: Int
    ) {

        val produto = lista[position]

        holder.txtNome.text = produto.nome

        holder.txtQuantidade.text =
            "Qtd: ${produto.quantidade}"

        holder.txtPreco.text =
            "R$ ${produto.preco}"
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}