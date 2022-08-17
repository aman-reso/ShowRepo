package `in`.conscent.mylibrary.adapter

import `in`.conscent.mylibrary.R
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.viewholders.SearchViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BreedListAdapter : RecyclerView.Adapter<SearchViewHolder>() {
    private var adapterList = ArrayList<SearchResponseModel>()

    open fun submitList(currentList: ArrayList<SearchResponseModel>) {
        println(currentList.size)
        val oldListCount = adapterList.size
        val currentListSize=currentList.size
        if (oldListCount == 0) {
            adapterList.addAll(currentList)
            notifyItemRangeInserted(oldListCount, currentListSize - 1)
        } else {
            adapterList.addAll(currentList)
            notifyItemRangeInserted(oldListCount, oldListCount + currentListSize-1)
        }
        println("size-->${adapterList.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item_holder, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val value = adapterList[position]
        holder.bindData(value)
    }
}