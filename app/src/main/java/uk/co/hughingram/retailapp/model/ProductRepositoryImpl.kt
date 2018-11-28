package uk.co.hughingram.retailapp.model

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Single
import uk.co.hughingram.retailapp.model.network.ApiClient

class ProductRepositoryImpl(private val apiClient: ApiClient, private val context: Context) : ProductRepository {

    override fun getAllProducts(): Single<List<Product>> =
        apiClient.getProductList().map {
            val db = Room
                .databaseBuilder(context, ProductDatabase::class.java, "foo")
                .build()
            db.productDao().insertMultipleProducts(it)
            it
        }

}