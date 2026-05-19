package com.matheusramalho.appshub.RoomDatabase.repository


import com.matheusramalho.appshub.RoomDatabase.data.Product
import com.matheusramalho.appshub.RoomDatabase.data.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {

    val allProducts: Flow<List<Product>> = dao.getAllProducts()

    suspend fun insert(product: Product) {
        dao.insert(product)
    }

    suspend fun update(product: Product) {
        dao.update(product)
    }

    suspend fun delete(product: Product) {
        dao.delete(product)
    }
}