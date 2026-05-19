package com.matheusramalho.appshub.RoomDatabase.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusramalho.appshub.R
import com.matheusramalho.appshub.RoomDatabase.data.Product
import com.matheusramalho.appshub.RoomDatabase.viewmodel.ProductViewModel
import com.matheusramalho.appshub.databinding.ActivityRoomDatabaseBinding


class RoomDatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomDatabaseBinding

    private val viewModel: ProductViewModel by viewModels()

    private lateinit var adapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_room_database)

        binding = ActivityRoomDatabaseBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = ProductAdapter(emptyList())

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()

            val price = binding.etPrice.text.toString().toDouble()

            val product = Product(
                    name = name,
                    price = price
                )

            viewModel.insert(product)

            binding.etName.text.clear()
            binding.etPrice.text.clear()
        }

        viewModel.products.observe(this) {

            adapter.updateList(it)
        }
    }
}