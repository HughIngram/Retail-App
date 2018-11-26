package model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// TODO baseUrl in gradle
internal class ApiClientImpl(baseUrl: String = "https://private-91dd6-iosassessment.apiary-mock.com/") : ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(ProductService::class.java)

    // TODO rx lib
    override fun getProductList(): Single<List<Product>> {
        val productListCall = service.listProducts()
        return Single.fromCallable {
            productListCall.execute().body()
        }.map { productList ->
            // TODO tidy this up with an ext method
            productList.products.map {
                Product(
                    identifier = it.identifier,
                    name = it.name,
                    brand = it.brand,
                    originalPrice = it.original_price.toString()
                )
            }
        }.subscribeOn(Schedulers.io())
    }

}

private interface ProductService {

    @GET("products")
    fun listProducts(): Call<ProductListApiModel>

}

private data class ProductListApiModel(
    val products: List<ProductApiModel>
)

private data class ProductApiModel(
    val identifier: Long,
    val name: String,
    val brand: String,
    val original_price: Double,
    val current_price: String,
    val currency: String,
    val image: ImageApiModel
)

private data class ImageApiModel(
    val id: Long,
    val url: String
)