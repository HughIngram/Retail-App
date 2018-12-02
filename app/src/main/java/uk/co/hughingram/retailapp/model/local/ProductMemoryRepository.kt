package uk.co.hughingram.retailapp.model.local

import io.reactivex.Observable
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.WritableProductRepository

class ProductMemoryRepository : WritableProductRepository {

    private val products = mutableListOf<Product>()

    override fun getAllProducts(): Observable<List<Product>> {
        return Observable.fromCallable {
            products.toList()
        }
    }

    override fun saveProducts(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
    }

}