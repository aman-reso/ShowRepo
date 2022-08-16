package `in`.conscent.mylibrary.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener constructor(private val layoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager?.childCount
        val totalItemCount = layoutManager?.itemCount
        val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            check(visibleItemCount, firstVisibleItemPosition, totalItemCount)
        }
    }

    private fun check(visibleItemCount: Int?, firstVisibleItemPosition: Int?, totalItemCount: Int?) {
        try {
            if (visibleItemCount!! + firstVisibleItemPosition!! >= totalItemCount!! && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}