package uk.co.hughingram.retailapp.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductRepositoryImpl(
    private val localRepository: WritableProductRepository,
    private val apiClient: ProductRepository
) : ProductRepository {

    override fun getAllProducts(): Observable<List<Product>> =
        Observable.concatArrayEager(
            localRepository.getAllProducts(),
            getProductsFromApi()
                .materialize()
                .filter { !it.isOnError }
                .dematerialize()
        ).subscribeOn(Schedulers.io())
            .map {
                it.sortedBy { product -> product.name }
            }

    private fun getProductsFromApi(): Observable<List<Product>> = apiClient.getAllProducts()
        .doOnNext {
            localRepository.saveProducts(it)
        }

}