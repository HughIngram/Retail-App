package uk.co.hughingram.retailapp.productlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_product_list.*
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.model.ApiClientProvider
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.view.BaseFragment
import uk.co.hughingram.retailapp.view.ProductRecycler

class ProductListFragment : BaseFragment(), ProductListView {

    override val fragmentLayout = R.layout.fragment_product_list

    private lateinit var presenter: ProductListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiClient = (activity?.application as ApiClientProvider).apiClient
        presenter = ProductListPresenterImpl(apiClient)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseAdapter()
        presenter.onAttach(this)
    }

    private fun initialiseAdapter() {
        product_recycler.layoutManager = LinearLayoutManager(context)
        product_recycler.adapter = ProductRecycler(mutableListOf())
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

    override fun onProductClick(): Observable<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateProductList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}