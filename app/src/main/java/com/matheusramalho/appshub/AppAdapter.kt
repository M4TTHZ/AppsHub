package com.matheusramalho.appshub

// =============================================================================
// ContatoAdapter.kt
// -----------------------------------------------------------------------------
// Adapter do RecyclerView (versão didática, mais comentada que a da Pokedex).
//
// Como funciona o RecyclerView (passo a passo):
//
//   [ Tela rolada para baixo ]
//          ↓
//   RecyclerView precisa de uma View pra mostrar.
//          ↓
//   Tem View na "piscina" (reciclada)?
//          NÃO  -> chama onCreateViewHolder() e cria.
//          SIM  -> reaproveita a View, só precisa atualizar dados.
//          ↓
//   Em ambos casos, chama onBindViewHolder() pra colar os dados certos.
//
// Por que isso é eficiente?
//   - Em uma lista de 1000 itens, ele cria umas 8-10 Views (só as visíveis).
//   - Reusar Views evita inflar XML toda hora (caro).
// =============================================================================

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AppAdapter(
    private val apps: List<App>,
    private val aoClicar: (App) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    // -------------------------------------------------------------------------
    // ViewHolder: guarda referências às Views de UM item, evitando findViewById
    // a cada bind.
    // Aqui chamamos findViewById no construtor da ViewHolder — assim a busca
    // acontece UMA VEZ por View, não a cada vez que rolamos a lista.
    // -------------------------------------------------------------------------
    inner class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNome: TextView = itemView.findViewById(R.id.txtName)
        val txtTelefone: TextView = itemView.findViewById(R.id.txtDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        // Infla o XML de UM item.
        // attachToRoot=false porque o RecyclerView é quem adiciona a View.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.txtNome.text = app.name
        holder.txtTelefone.text = app.description
        holder.itemView.setOnClickListener { aoClicar(app) }
    }

    override fun getItemCount(): Int = apps.size
}