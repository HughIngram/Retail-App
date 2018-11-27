package uk.co.hughingram.retailapp.productlist

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import uk.co.hughingram.retailapp.model.ApiClient

class ProductListPresenterImpl(private val apiClient: ApiClient) : ProductListPresenter {

    private val disposables = CompositeDisposable()

    override fun onAttach(view: ProductListView) {
        val getProducts = apiClient.getProductList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.updateProductList(it)
                    Log.i("test123", it.toString())
                },
                onError = {
                    Log.e("test123", "error", it)
                }
            )
        disposables += getProducts
    }

    override fun onDetach() {
        disposables.clear()
    }

}