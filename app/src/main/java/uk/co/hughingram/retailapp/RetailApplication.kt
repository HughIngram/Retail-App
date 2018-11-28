package  uk.co.hughingram.retailapp

import android.app.Application
import uk.co.hughingram.retailapp.model.network.ApiClient
import uk.co.hughingram.retailapp.model.network.ApiClientImpl
import uk.co.hughingram.retailapp.model.ApiClientProvider

class RetailApplication : Application(), ApiClientProvider {

    override var apiClient = ApiClientImpl(BuildConfig.SERVER_URL) as ApiClient

}