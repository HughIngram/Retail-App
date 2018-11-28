package uk.co.hughingram.retailapp.model

import uk.co.hughingram.retailapp.model.network.ApiClient

interface ApiClientProvider {
    var apiClient: ApiClient
}

interface ProductRepositoryProvider {
    var productRepository: ProductRepository
}
