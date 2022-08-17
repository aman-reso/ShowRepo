package `in`.conscent.mylibrary.adapter

import `in`.conscent.mylibrary.R
import `in`.conscent.mylibrary.Utility
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.viewholders.SearchViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.RecyclerView

class BreedSearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    private var asyncListDiffer = AsyncListDiffer(this, Utility().diffUtil)

    internal fun submitList(searchList: ArrayList<SearchResponseModel>) {
        asyncListDiffer.submitList(searchList.map {
            it.copy()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_holder, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val value = asyncListDiffer.currentList[position]
        holder.bindData(value)
    }
}