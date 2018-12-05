package uk.co.hughingram.retailapp

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductRepository

class MockApiClient(private val productList: List<Product>, private val loadSlowly: Boolean = false) :
    ProductRepository {

    var finishLoading = false

    override fun getAllProducts(): Observable<List<Product>> = Observable.fromCallable {
        if (loadSlowly) {
            while (!finishLoading) {
                // wait
            }
        }
        productList
    }.subscribeOn(Schedulers.io())

}