package `in`.conscent.mylibrary.repository

import `in`.conscent.mylibrary.apimodule.safeApiCall
import `in`.conscent.mylibrary.network.interfaces.NetworkInterface
import javax.inject.Inject

class NetworkRepository @Inject constructor(var networkInterface: NetworkInterface) {
    suspend fun getSearchData(page: Int) = safeApiCall {
        networkInterface.getAllSearchResult(page=page)
    }

    suspend fun getCategoriesData()= safeApiCall {
        networkInterface.getAllCategories()
    }
}