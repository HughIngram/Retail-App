package uk.co.hughingram.retailapp.model.network

import io.reactivex.Observable
import uk.co.hughingram.retailapp.model.Product

interface ApiClient {

    /**
     * Get an observable to fetch the list of products from the web service.
     */
    fun getProductList(): Observable<List<Product>>

}