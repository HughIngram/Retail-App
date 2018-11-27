package uk.co.hughingram.retailapp.productlist

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import model.ApiClientProvider
import uk.co.hughingram.retailapp.R
import uk.co.hughingram.retailapp.view.BaseFragment

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
        presenter.onAttach(this)
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