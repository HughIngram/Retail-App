package uk.co.hughingram.retailapp.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import uk.co.hughingram.retailapp.model.local.ProductMemoryRepository

class ProductRepositoryImpl(
    private val localRepository: WritableProductRepository,
    private val apiClient: ProductRepository
) : ProductRepository {

    private val memoryRepository = ProductMemoryRepository()

    override fun getAllProducts(): Observable<List<Product>> =
        Observable.concatArrayEager(
            getProductsFromMemory(),
            getProductsFromDisk(),
            getProductsFromApi()
                .materialize()
                .filter { !it.isOnError }
                .dematerialize()
        ).subscribeOn(Schedulers.io())
            .map {
                it.sortedBy { product -> product.name }
            }

    private fun getProductsFromMemory() = memoryRepository.getAllProducts()

    private fun getProductsFromDisk() = localRepository.getAllProducts()

    private fun getProductsFromApi(): Observable<List<Product>> = apiClient.getAllProducts()
        .doOnNext {
            memoryRepository.saveProducts(it)
            localRepository.saveProducts(it)
        }

}