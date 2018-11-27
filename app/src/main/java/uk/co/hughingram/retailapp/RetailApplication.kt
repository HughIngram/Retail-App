package  uk.co.hughingram.retailapp

import android.app.Application
import uk.co.hughingram.retailapp.model.ApiClient
import uk.co.hughingram.retailapp.model.ApiClientImpl
import uk.co.hughingram.retailapp.model.ApiClientProvider

class RetailApplication : Application(), ApiClientProvider {

    override var apiClient = ApiClientImpl(BuildConfig.SERVER_URL) as ApiClient

}