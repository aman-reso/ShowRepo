package `in`.conscent.mylibrary.network.interfaces

import `in`.conscent.mylibrary.constants.AppConstants
import `in`.conscent.mylibrary.constants.AppConstants.DEFAULT_PAGE_SIZE
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkInterface {

    @GET("/v1/categories")
    @Headers("x-api-key:${AppConstants.API_SECRET_KEY}")
    suspend fun getAllCategories(): CategoryResponse

    @GET("/v1/breeds?page=1&limit=1")
    @Headers("x-api-key:${AppConstants.API_SECRET_KEY}")
    suspend fun getAllSearchResult(
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE,
        @Query("page") page: Int? = 1
    ): ArrayList<SearchResponseModel>
}

fun getHeaders() {
    val hashMap = HashMap<String, String>()
    hashMap["x-api-key"] = AppConstants.API_SECRET_KEY
}