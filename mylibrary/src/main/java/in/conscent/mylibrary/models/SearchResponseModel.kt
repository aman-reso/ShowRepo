package `in`.conscent.mylibrary.models

import com.google.gson.annotations.SerializedName


data class SearchResponseModel(
    @SerializedName("weight") var weightModel: WeightModel? = WeightModel(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: ImageModel? = ImageModel()
)