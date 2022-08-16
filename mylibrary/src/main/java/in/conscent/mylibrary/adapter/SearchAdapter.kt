package `in`.conscent.mylibrary.adapter

import `in`.conscent.mylibrary.R
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.viewholders.SearchViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    private var list = ArrayList<SearchResponseModel>()

    open fun submitList(list: ArrayList<SearchResponseModel>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_holder, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val value = list[position]
        holder.bindData(value)
    }
}