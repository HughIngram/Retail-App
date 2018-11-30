package uk.co.hughingram.retailapp.productlist

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.view.View
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.android.synthetic.main.fragment_product_list.*
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductRepositoryProvider
import uk.co.hughingram.retailapp.view.BaseFragment

class ProductListFragment : BaseFragment(), ProductListView {

    override val fragmentLayout = R.layout.fragment_product_list
    override val isFullScreen = false

    private lateinit var presenter: ProductListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (activity?.application as ProductRepositoryProvider).productRepository
        presenter = ProductListPresenterImpl(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseAdapter()
        presenter.onAttach(this)
    }

    private fun initialiseAdapter() {
        val itemClickListener = { s: String -> productClickEmitter.onNext(s) }
        product_recycler.layoutManager = GridLayoutManager(context, 2)
        product_recycler.adapter = ProductRecycler(mutableListOf(), itemClickListener)
        listOf(GridLayoutManager.VERTICAL, GridLayoutManager.HORIZONTAL).map {
            DividerItemDecoration(context, it)
        }.map {
            product_recycler.addItemDecoration(it)
        }
    }

    override fun updateProductList(products: List<Product>) {
        val adapter = product_recycler.adapter as ProductRecycler
        adapter.products = products
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    override fun onSwipeRefresh(): Observable<Unit> = Observable.create { emitter ->
        swipe_container.setOnRefreshListener { emitter.onNext(Unit) }
    }

    override fun showLoading() {
        swipe_container.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_container.isRefreshing = false
    }

    private lateinit var productClickEmitter: ObservableEmitter<String>

    override fun onProductClick(): Observable<String> = Observable.create { emitter ->
        productClickEmitter = emitter
    }

    override fun openImage(url: String) {
        val directions =
            ProductListFragmentDirections.actionProductListFragmentToImageFragment(url)
        findNavController().navigate(directions)
    }
}