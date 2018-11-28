package  uk.co.hughingram.retailapp

import android.app.Application
import uk.co.hughingram.retailapp.model.ProductRepository
import uk.co.hughingram.retailapp.model.ProductRepositoryImpl
import uk.co.hughingram.retailapp.model.ProductRepositoryProvider
import uk.co.hughingram.retailapp.model.network.ApiClient
import uk.co.hughingram.retailapp.model.network.ApiClientImpl

class RetailApplication : Application(), ProductRepositoryProvider {

    private var apiClient = ApiClientImpl(BuildConfig.SERVER_URL) as ApiClient

    override var productRepository = ProductRepositoryImpl(apiClient, this) as ProductRepository

}