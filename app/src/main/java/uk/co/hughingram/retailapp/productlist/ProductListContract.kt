package uk.co.hughingram.retailapp.productlist

import io.reactivex.Observable
import uk.co.hughingram.retailapp.model.Product

interface ProductListPresenter {

    fun onAttach(view: ProductListView)

    fun onDetach()

}

interface ProductListView {

    fun updateProductList(products: List<Product>)

    fun onProductClick(): Observable<Long>

    fun updateProductList()

    fun onSwipeRefresh(): Observable<Unit>

    fun showLoading()

    fun hideLoading()

}

