package uk.co.hughingram.retailapp.model


data class Product(
    val identifier: Long,
    val name: String,
    val brand: String,
    val originalPrice: Double,
    val currentPrice: Double,
    val currency: String,
    val image: ProductImage
)

data class ProductImage(
    val url: String,
    val id: Long
)
