package `in`.conscent.mylibrary

import `in`.conscent.mylibrary.models.SearchResponseModel
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil

internal class Utility {
   internal var diffUtil = object : DiffUtil.ItemCallback<SearchResponseModel>() {
        override fun areItemsTheSame(oldItem: SearchResponseModel, newItem: SearchResponseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResponseModel, newItem: SearchResponseModel): Boolean {
            return oldItem == newItem
        }

    }
}