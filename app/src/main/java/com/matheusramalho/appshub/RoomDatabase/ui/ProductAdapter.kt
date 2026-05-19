package com.matheusramalho.appshub.RoomDatabase.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matheusramalho.appshub.RoomDatabase.data.Product
import com.matheusramalho.appshub.R


class ProductAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: android.view.View)
        : RecyclerView.ViewHolder(view) {

        val txtProduct: TextView =
            view.findViewById(R.id.txtProduct)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product_room_database, parent, false)

        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = products[position]

        holder.txtProduct.text =
            "${product.name} - R$ ${product.price}"
    }

    fun updateList(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }
}