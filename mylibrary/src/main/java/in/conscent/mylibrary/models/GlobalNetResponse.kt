package `in`.conscent.mylibrary.models

sealed class GlobalNetResponse<T> {
    data class Success<T>(var value:T) : GlobalNetResponse<T>()
    data class NetworkFailure<T>(var error: String) : GlobalNetResponse<T>()
}