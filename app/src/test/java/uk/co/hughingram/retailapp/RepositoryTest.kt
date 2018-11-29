package uk.co.hughingram.retailapp

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.hughingram.retailapp.model.Product
import uk.co.hughingram.retailapp.model.ProductRepository
import uk.co.hughingram.retailapp.model.ProductRepositoryImpl
import uk.co.hughingram.retailapp.model.WritableProductRepository

class RepositoryTest {

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun repositoryWorksWithBrokenApiClient() {
        // GIVEN - a repository with a local data source and an api data source
        val expectedProducts = randomProductList()
        val localDataSource = object : WritableProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.fromCallable { expectedProducts }
            override fun saveProducts(products: List<Product>) = Unit
        }
        // WHEN - the api client returns an error
        val remoteDataSource = object : ProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.error(Throwable())
        }
        val repository = ProductRepositoryImpl(localDataSource, remoteDataSource)   // subject under test

        // THEN - the repository should return the data from the local data source
        val testObserver = TestObserver<List<Product>>()
        repository.getAllProducts().subscribeWith(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue(expectedProducts)
        testObserver.assertValueCount(1)
    }

    @Test
    fun repositorySortsItemsFromApiClientCorrectly() {
        // GIVEN - a repository with an empty local data source, and a remote data source
        val expectedProducts = listOf(
            generateRandomProduct().copy(name = "2"),
            generateRandomProduct().copy(name = "1"),
            generateRandomProduct().copy(name = "3")
        )

        // local ds empty
        val localDataSource = object : WritableProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.fromCallable { listOf<Product>() }
            override fun saveProducts(products: List<Product>) = Unit
        }
        // WHEN - the api client returns an error
        val remoteDataSource = object : ProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.fromCallable { expectedProducts }
        }
        val repository = ProductRepositoryImpl(localDataSource, remoteDataSource)   // subject under test

        // THEN - the repository should return the data from the local data source
        val testObserver = TestObserver<List<Product>>()
        repository.getAllProducts().subscribeWith(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValueCount(2)
        val apiValue = testObserver.values().toList().sortedBy { it.size }.last()
        assertEquals(expectedProducts.sortedBy { it.name }, apiValue)
    }

}
