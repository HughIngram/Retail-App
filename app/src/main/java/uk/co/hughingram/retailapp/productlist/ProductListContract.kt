package uk.co.hughingram.retailapp.productlist

import io.reactivex.Observable
import uk.co.hughingram.retailapp.model.Product

interface ProductListPresenter {

    fun onAttach(view: ProductListView)

    fun onDetach()

}

interface ProductListView {

    fun updateProductList(products: List<Product>)

    /**
     * Emits the url of the image for the clicked product.
     * // TODO pass a serialized Product or a product ID
     */
    fun onProductClick(): Observable<String>

    // TODO pass a serialized Product or a product ID
    fun openImage(url: String)

    fun onSwipeRefresh(): Observable<Unit>

    fun showLoading()

    fun hideLoading()

}

