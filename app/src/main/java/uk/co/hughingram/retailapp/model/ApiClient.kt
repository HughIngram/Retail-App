package uk.co.hughingram.retailapp.model

import io.reactivex.Single

interface ApiClient {

    /**
     * Get an observable to fetch the list of products from the web service.
     */
    fun getProductList(): Single<List<Product>>

}
