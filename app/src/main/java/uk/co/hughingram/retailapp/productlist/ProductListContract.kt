package uk.co.hughingram.retailapp.productlist

import io.reactivex.Observable

interface ProductListPresenter {

    fun onAttach(view: ProductListView)

    fun onDetach()

}

interface ProductListView {

    fun onProductClick(): Observable<Long>

    fun updateProductList()

}

