package uk.co.hughingram.retailapp.model

import io.reactivex.Single

interface ProductRepository {

    fun getAllProducts(): Single<List<Product>>

}