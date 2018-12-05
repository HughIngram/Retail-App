package uk.co.hughingram.retailapp.productlist

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import uk.co.hughingram.retailapp.model.ProductRepository

internal class ProductListPresenterImpl(private val productListRepository: ProductRepository) : ProductListPresenter {

    private val disposables = CompositeDisposable()

    override fun onAttach(view: ProductListView) {
        refreshProductList(view)
        disposables += view.onSwipeRefresh().subscribe {
            refreshProductList(view)
        }
        disposables += view.onProductClick().subscribe {
            view.openImage(it)
        }
    }

    private fun refreshProductList(view: ProductListView) {
        disposables += productListRepository.getAllProducts()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showLoading()
            }
            .doFinally {
                view.hideLoading()
            }
            .subscribeBy(
                onNext = {
                    view.updateProductList(it)
                    if (it.isNotEmpty()) {
                        view.hideLoading()
                    }
                },
                onError = {
                    Log.e("test123", "error fetching product list", it)
                }
            )
    }

    override fun onDetach() {
        disposables.clear()
    }

}