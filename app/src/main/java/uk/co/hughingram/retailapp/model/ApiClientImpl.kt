package uk.co.hughingram.retailapp.model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

internal class ApiClientImpl(baseUrl: String) : ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private val service = retrofit.create(ProductService::class.java)

    override fun getProductList(): Single<List<Product>> =
        service.listProducts().map {
            it.toDomainObject()
        }.subscribeOn(Schedulers.io())

}

private interface ProductService {

    @GET("products")
    fun listProducts(): Single<ProductListApiModel>

}

private data class ProductListApiModel(
    val products: List<ProductApiModel>
)

private data class ProductApiModel(
    val identifier: Long,
    val name: String,
    val brand: String,
    val original_price: Double,
    val current_price: Double,
    val currency: String,
    val image: ImageApiModel
)

private data class ImageApiModel(
    val id: Long,
    val url: String
)

private fun ProductListApiModel.toDomainObject(): List<Product> = products.map {
    Product(
        identifier = it.identifier,
        name = it.name,
        brand = it.brand,
        originalPrice = it.original_price,
        currentPrice = it.current_price,
        currency = it.currency,
        image = ProductImage(it.image.url, it.image.id)
    )
}

