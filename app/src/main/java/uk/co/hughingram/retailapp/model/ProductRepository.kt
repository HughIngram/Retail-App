package uk.co.hughingram.retailapp.model

import io.reactivex.Observable

interface ProductRepository {

    fun getAllProducts(): Observable<List<Product>>

}

interface WritableProductRepository : ProductRepository {

    fun saveProducts(products: List<Product>)

}