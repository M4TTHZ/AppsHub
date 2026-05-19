package com.matheusramalho.appshub.RoomDatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.matheusramalho.appshub.RoomDatabase.data.AppDatabase
import com.matheusramalho.appshub.RoomDatabase.data.Product
import com.matheusramalho.appshub.RoomDatabase.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application)
    : AndroidViewModel(application) {

    private val repository: ProductRepository

    val products: androidx.lifecycle.LiveData<List<Product>>

    init {
        val dao = AppDatabase
                .getDatabase(application)
                .productDao()

        repository = ProductRepository(dao)

        products = repository
                .allProducts
                .asLiveData()
    }

    fun insert(product: Product) = viewModelScope.launch {
            repository.insert(product)
        }

    fun update(product: Product) = viewModelScope.launch {
            repository.update(product)
        }

    fun delete(product: Product) = viewModelScope.launch {
            repository.delete(product)
        }
}