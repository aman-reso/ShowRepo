package `in`.conscent.mylibrary.repository

import `in`.conscent.mylibrary.apimodule.safeApiCall
import `in`.conscent.mylibrary.models.GlobalNetResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.network.interfaces.NetworkInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(var networkInterface: NetworkInterface) {
    suspend fun getSearchData(page: Int) = safeApiCall {
        networkInterface.getAllSearchResult(page = page)
    }

    suspend fun getCategoriesData() = safeApiCall {
        networkInterface.getAllCategories()
    }

    suspend fun getCatBreedBasedOnSearch(inputString: String) = safeApiCall {
        networkInterface.getCatBreedBasedOnSearch(inputString)
    }

    suspend fun executeApiForSearchBreed(inputString: String): Flow<ArrayList<SearchResponseModel>?> {
        val networkResponse = safeApiCall { networkInterface.getCatBreedBasedOnSearch(inputString) }
        return flow {
            when (networkResponse) {
                is GlobalNetResponse.Success -> {
                    emit(networkResponse.value)
                }
                else -> {}
            }
        }
    }


}