package `in`.conscent.mylibrary

import `in`.conscent.mylibrary.adapter.SearchAdapter
import `in`.conscent.mylibrary.databinding.ActivitySearchBinding
import `in`.conscent.mylibrary.extensions.CustomSpinnerAdapter
import `in`.conscent.mylibrary.extensions.PaginationScrollListener
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.viewmodel.MainViewModel
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private var binding: ActivitySearchBinding? = null
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private val mainViewModel: MainViewModel? by viewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private var recyclerView: RecyclerView? = null
    private var spinner: Spinner? = null

    private var currentPage = 1
    private var isCurrentlyLoading: Boolean = true
    private var totalPageCount = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initRecyclerView()
        setUpObservers(this)
    }

    private fun initRecyclerView() {
        recyclerView = binding?.searchResultRecyclerView
        spinner = binding?.spinnerAndroid
        recyclerView?.apply {
            adapter = searchAdapter
            layoutManager = linearLayoutManager
        }
        getSearchResponse(currentPage)
    }


    private fun setUpObservers(searchActivity: SearchActivity) {
        mainViewModel?.apply {
            this.searchLiveData.observe(searchActivity) {
                isCurrentlyLoading = false;
                if (it != null) {
                    searchAdapter.submitList(it)
                }
            }
            this.categoryLiveData.observe(this@SearchActivity) {
                if (it != null) {
                    createSpinner(it)
                }
            }
        }
        mainViewModel?.getCategoriesResponse()
    }

    private fun getSearchResponse(page: Int) {
        mainViewModel?.getSearchResponse(page)
    }

    private fun createSpinner(categoryResponse: CategoryResponse) {
        val userSpinnerAdapter = CustomSpinnerAdapter(this, R.layout.custom_spinner_item, categoryResponse)
        spinner?.adapter = userSpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.selectedItem as CategoryResponse.CategoryResponseItem
                Toast.makeText(this@SearchActivity, selectedItem.name, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                println(p0)
            }
        }
    }

}

/**
 * private fun attachCustomScrollListener() {
val paginationScrollListener = object : PaginationScrollListener(layoutManager = linearLayoutManager) {
override fun loadMoreItems() {
isCurrentlyLoading = true
currentPage += 1
getSearchResponse(currentPage)
}

override fun isLastPage(): Boolean {
return false
}

override fun isLoading(): Boolean {
return isCurrentlyLoading
}

}
recyclerView?.addOnScrollListener(paginationScrollListener)
}
 */

/**
 * made these things as commented because initially expect there will be
 * search api based on what keyword user has type
 *
 */

/**
 * private fun setUpListeners() {
binding?.searchEditText?.customTextChangesListener()?.filterNot { it.isNullOrBlank() }
?.debounce(DEBOUNCE_TIME_FOR_SEARCH)
?.flatMapLatest {
executeSearch(term = it.toString())
}
?.onEach { it ->
//here is the result which will be displayed with the views
}?.launchIn(lifecycleScope)
}

private suspend fun executeSearch(term: String): Flow<SearchResponseModel> {
return flow {

}
}
 */
