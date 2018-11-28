package uk.co.hughingram.retailapp

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import uk.co.hughingram.retailapp.model.network.ApiClient
import uk.co.hughingram.retailapp.model.Product

class MockApiClient(private val productList: List<Product>, private val loadSlowly: Boolean = false) :
    ApiClient {

    var finishLoading = false

    override fun getProductList(): Single<List<Product>> = Single.fromCallable {
        if (loadSlowly) {
            while (!finishLoading) {
                // wait
            }
        }
        productList
    }.subscribeOn(Schedulers.io())

}