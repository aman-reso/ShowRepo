package `in`.conscent.mylibrary.models


class CategoryResponse : ArrayList<CategoryResponse.CategoryResponseItem>(){
    data class CategoryResponseItem(var id: Int,var name: String)
}