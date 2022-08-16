package `in`.conscent.mylibrary.models

import com.google.gson.annotations.SerializedName


data class ImageModel(
    @SerializedName("id") var id: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("url") var url: String? = null
)