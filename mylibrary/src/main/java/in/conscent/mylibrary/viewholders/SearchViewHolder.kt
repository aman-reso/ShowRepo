package `in`.conscent.mylibrary.viewholders

import `in`.conscent.mylibrary.R
import `in`.conscent.mylibrary.models.SearchResponseModel
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var text: TextView = view.findViewById(R.id.searchTextView)
    private var image: ImageView = view.findViewById(R.id.catImage)
    fun bindData(value: SearchResponseModel) {
        text.text = value.name
        val imageUrl = value.image?.url
        if (imageUrl != null) {
            Picasso.get().load(imageUrl).into(image)
        }
    }
}