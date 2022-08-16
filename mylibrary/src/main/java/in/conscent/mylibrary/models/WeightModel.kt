package `in`.conscent.mylibrary.models

import com.google.gson.annotations.SerializedName


data class WeightModel(
    @SerializedName("imperial") var imperial: String? = null,
    @SerializedName("metric") var metric: String? = null
)