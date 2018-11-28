package uk.co.hughingram.retailapp

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.network.ApiClient

class MockApiClient(private val productList: List<Product>, private val loadSlowly: Boolean = false) :
    ApiClient {

    var finishLoading = false

    override fun getProductList(): Observable<List<Product>> = Observable.fromCallable {
        if (loadSlowly) {
            while (!finishLoading) {
                // wait
            }
        }
        productList
    }.subscribeOn(Schedulers.io())

}