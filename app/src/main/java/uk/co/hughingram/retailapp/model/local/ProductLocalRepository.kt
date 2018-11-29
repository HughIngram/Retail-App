package uk.co.hughingram.retailapp.model.local

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Observable
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductDatabase
import uk.co.hughingram.retailapp.model.WritableProductRepository

class ProductLocalRepository(context: Context) : WritableProductRepository {

    private val productDao = Room
        .databaseBuilder(context, ProductDatabase::class.java, "products_db")
        .build()
        .productDao()

    override fun getAllProducts(): Observable<List<Product>> = productDao.getAll().toObservable()

    override fun saveProducts(products: List<Product>) = productDao.insertMultipleProducts(products)

}