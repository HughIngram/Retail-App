package uk.co.hughingram.retailapp

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import uk.co.hughingram.retailapp.model.*
import java.util.*
import kotlin.random.Random

class RepositoryTest {

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun repositoryWorksWithBrokenApiClient() {
        // GIVEN - a repository with a local data source and an api data source
        val expectedProducts = listOf(generateRandomProduct(), generateRandomProduct(), generateRandomProduct())
        val localDataSource = object : WritableProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.fromCallable { expectedProducts }
            override fun saveProducts(products: List<Product>) = Unit
        }
        // WHEN - the api client returns an error
        val remoteDataSource = object : ProductRepository {
            override fun getAllProducts(): Observable<List<Product>> = Observable.error(Throwable())
        }
        val repository = ProductRepositoryImpl(localDataSource, remoteDataSource)

        // THEN - the repository should return the data from the local data source
        val testObserver = TestObserver<List<Product>>()
        repository.getAllProducts().subscribeWith(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertValue(expectedProducts)
        testObserver.assertValueCount(1)
    }

    private fun generateRandomProduct() = Product(
        identifier = Random.nextLong(),
        name = UUID.randomUUID().toString(),
        brand = UUID.randomUUID().toString(),
        originalPrice = Random.nextDouble(),
        currentPrice = Random.nextDouble(),
        currency = UUID.randomUUID().toString(),
        image = ProductImage(
            url = UUID.randomUUID().toString(),
            id = Random.nextLong()
        )
    )

}
