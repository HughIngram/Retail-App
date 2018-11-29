package uk.co.hughingram.retailapp.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProductRepositoryImpl(private val localRepository: ProductRepository, private val apiClient: ProductRepository) :
    ProductRepository {

    override fun getAllProducts(): Observable<List<Product>> =
        Observable.concatArrayEager(
            localRepository.getAllProducts(),
            getProductsFromApi()
                .materialize()
                .filter { !it.isOnError }
                .dematerialize()
        ).subscribeOn(Schedulers.io())

    private fun getProductsFromApi(): Observable<List<Product>> = apiClient.getAllProducts()
        .doOnNext {
            Unit
//             TODO add another interface WriteableProductRepository : ProductRepository
//            productDao.insertMultipleProducts(it)
        }

}