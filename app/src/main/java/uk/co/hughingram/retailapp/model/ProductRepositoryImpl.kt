package uk.co.hughingram.retailapp.model

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductRepositoryImpl(private val apiClient: ProductRepository, context: Context) : ProductRepository {

    private val productDao = Room
        .databaseBuilder(context, ProductDatabase::class.java, "products_db")
        .build()
        .productDao()

    override fun getAllProducts(): Observable<List<Product>> =
        Observable.concatArrayEager(
            getProductsFromDb(),
            getProductsFromApi()
        ).subscribeOn(Schedulers.io())

    private fun getProductsFromDb(): Observable<List<Product>> =
        productDao.getAll().toObservable()

    private fun getProductsFromApi(): Observable<List<Product>> = apiClient.getAllProducts()
        .doOnNext {
            productDao.insertMultipleProducts(it)
        }

}