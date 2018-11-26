package model

import io.reactivex.Single

interface ApiClient {
    fun getProductList(): Single<List<Product>>
}

data class Product(val identifier: Long, val name: String, val brand: String, val originalPrice: String)

// TODO use a sealed class for converting currency to enum
enum class Currency { EURO, US_DOLLAR }