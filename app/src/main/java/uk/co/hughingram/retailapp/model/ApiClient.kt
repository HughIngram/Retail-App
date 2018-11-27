package uk.co.hughingram.retailapp.model

import io.reactivex.Single

interface ApiClient {
    fun getProductList(): Single<List<Product>>
}

