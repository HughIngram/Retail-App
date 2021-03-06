package  uk.co.hughingram.retailapp

import android.app.Application
import android.content.Context
import uk.co.hughingram.retailapp.model.local.ProductLocalRepository
import uk.co.hughingram.retailapp.model.ProductRepository
import uk.co.hughingram.retailapp.model.ProductRepositoryImpl
import uk.co.hughingram.retailapp.model.ProductRepositoryProvider
import uk.co.hughingram.retailapp.model.network.ApiClientImpl

class RetailApplication : Application(), ProductRepositoryProvider {

    override lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        val apiClient = ApiClientImpl(BuildConfig.SERVER_URL)
        val localDataSource = ProductLocalRepository(this as Context)
        productRepository = ProductRepositoryImpl(localDataSource, apiClient)
    }

}