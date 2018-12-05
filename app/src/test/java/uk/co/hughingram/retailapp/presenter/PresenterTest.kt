package uk.co.hughingram.retailapp.presenter

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.hughingram.retailapp.MockApiClient
import uk.co.hughingram.retailapp.productlist.ProductListPresenter
import uk.co.hughingram.retailapp.productlist.ProductListPresenterImpl
import uk.co.hughingram.retailapp.productlist.ProductListView
import uk.co.hughingram.retailapp.randomProductList

class PresenterTest {

    // subject under test
    private lateinit var presenter: ProductListPresenter

    @Mock
    private lateinit var mockView: ProductListView

    private lateinit var refreshEmitter: ObservableEmitter<Unit>

    private lateinit var productClickEmitter: ObservableEmitter<String>

    private val products = randomProductList()

    @Before
    fun before() {
        // don't use threads which the JVM does not have access to
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        // set up mocks
        MockitoAnnotations.initMocks(this)
        val mockApiClient = MockApiClient(products)
        presenter = ProductListPresenterImpl(mockApiClient)
        whenever(mockView.onSwipeRefresh()).thenReturn(Observable.create { e ->
            refreshEmitter = e
        })
        whenever(mockView.onProductClick()).thenReturn(Observable.create { e ->
            productClickEmitter = e
        })
        presenter.onAttach(mockView)
    }

    @Test
    fun pageRefreshedOnSwipe() {
        // GIVEN I have the list of products open
        verify(mockView, times(1)).updateProductList(any())

        // WHEN I scroll all the way to the top THEN swipe down from the top
        refreshEmitter.onNext(Unit)

        // THEN The list of products should be refreshed
        verify(mockView, times(2)).updateProductList(any())
    }

    @Test
    fun imageOpenedOnClick() {
        // GIVEN I have the product list open
        verify(mockView, never()).openImage(any())

        // WHEN tapping on a product
        val expectedImageUrl = products.first().image.url
        productClickEmitter.onNext(expectedImageUrl)

        // THEN the image for the product I tapped should be opened
        verify(mockView, times(1)).openImage(expectedImageUrl)
    }

}