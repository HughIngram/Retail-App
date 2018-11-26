package  uk.co.hughingram.retailapp

import android.app.Application
import model.ApiClient
import model.ApiClientImpl
import model.ApiClientProvider

class RetailApplication : Application(), ApiClientProvider {

    override var apiClient = ApiClientImpl() as ApiClient

}