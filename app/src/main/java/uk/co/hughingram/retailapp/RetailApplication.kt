package  uk.co.hughingram.retailapp

import android.app.Application
import android.content.Context
import uk.co.hughingram.retailapp.model.ProductRepository
import uk.co.hughingram.retailapp.model.ProductRepositoryImpl
import uk.co.hughingram.retailapp.model.ProductRepositoryProvider
import uk.co.hughingram.retailapp.model.network.ApiClient
import uk.co.hughingram.retailapp.model.network.ApiClientImpl

class RetailApplication : Application(), ProductRepositoryProvider {

    override lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        val apiClient = ApiClientImpl(BuildConfig.SERVER_URL) as ApiClient
        productRepository = ProductRepositoryImpl(apiClient, this as Context)
    }

}