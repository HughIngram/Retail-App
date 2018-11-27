package uk.co.hughingram.retailapp

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import uk.co.hughingram.retailapp.model.ApiClient
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductImage
import java.util.*
import kotlin.random.Random

class MockApiClient(private val loadSlowly: Boolean = false) : ApiClient {

    var finishLoading = false

    override fun getProductList(): Single<List<Product>> = Single.fromCallable {
        if (loadSlowly) {
            while (!finishLoading) {
                // wait
            }
        }
        listOf(
            generateRandomProduct(),
            generateRandomProduct(),
            generateRandomProduct()
        )
    }.subscribeOn(Schedulers.io())

    private fun generateRandomProduct() = Product(
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
}