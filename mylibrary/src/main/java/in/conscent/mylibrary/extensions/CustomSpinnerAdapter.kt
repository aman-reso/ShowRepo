package `in`.conscent.mylibrary.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import `in`.conscent.mylibrary.models.CategoryResponse.CategoryResponseItem
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

class CustomSpinnerAdapter(// Your sent context
      context: Context, textViewResourceId: Int, values: ArrayList<CategoryResponseItem>
) : ArrayAdapter<CategoryResponseItem>(context, textViewResourceId, values) {
    // Your custom values for the spinner (User)
    private val values: ArrayList<CategoryResponseItem>
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): CategoryResponseItem {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].name

        // And finally return your dynamic (or custom) view for each spinner item
        return label
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val label = super.getDropDownView(position, convertView, parent) as? TextView?
        label?.setTextColor(Color.BLACK)
        label?.text = values[position].name
        return label
    }

    init {
        this.values = values
    }
}