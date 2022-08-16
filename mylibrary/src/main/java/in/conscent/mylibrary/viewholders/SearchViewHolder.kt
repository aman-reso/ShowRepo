package `in`.conscent.mylibrary.viewholders

import `in`.conscent.mylibrary.R
import `in`.conscent.mylibrary.models.SearchResponseModel
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var text: TextView = view.findViewById(R.id.searchTextView)
    fun bindData(value: SearchResponseModel) {
        text.text = value.name
    }
}