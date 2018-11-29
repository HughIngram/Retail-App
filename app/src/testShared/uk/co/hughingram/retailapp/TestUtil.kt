package uk.co.hughingram.retailapp

import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductImage
import java.util.*
import kotlin.random.Random

fun generateRandomProduct() = Product(
    identifier = Random.nextLong(),
    name = UUID.randomUUID().toString(),
    brand = UUID.randomUUID().toString(),
    originalPrice = Random.nextDouble(),
    currentPrice = Random.nextDouble(),
    currency = UUID.randomUUID().toString(),
    image = ProductImage(
        url = UUID.randomUUID().toString(),
        id = Random.nextLong()
    )
)

fun randomProductList(): List<Product> =
    listOf(generateRandomProduct(), generateRandomProduct(), generateRandomProduct())
