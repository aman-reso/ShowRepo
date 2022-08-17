package `in`.conscent.mylibrary.apimodule

import `in`.conscent.mylibrary.models.GlobalNetResponse
import `in`.conscent.mylibrary.viewmodel.io_dispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException


suspend fun <T> safeApiCall(apiCall: suspend () -> T): GlobalNetResponse<T> {
    return withContext(io_dispatcher) {
        try {
            GlobalNetResponse.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val message = throwable.localizedMessage
                    GlobalNetResponse.NetworkFailure(message)
                }
                else -> {
                    val message = throwable.localizedMessage
                    GlobalNetResponse.NetworkFailure(message)
                }
            }
        }
    }
}
//
//private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
//    return try {
//        throwable.response()?.errorBody()?.source()?.let {
//            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
//            moshiAdapter.fromJson(it)
//        }
//    } catch (exception: Exception) {
//        null
//    }
//}
