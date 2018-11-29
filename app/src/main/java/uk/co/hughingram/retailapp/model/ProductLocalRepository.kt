package uk.co.hughingram.retailapp.model

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Observable

class ProductLocalRepository(context: Context) : ProductRepository {

    private val productDao = Room
        .databaseBuilder(context, ProductDatabase::class.java, "products_db")
        .build()
        .productDao()

    override fun getAllProducts(): Observable<List<Product>> = productDao.getAll().toObservable()

}