package `in`.conscent.mylibrary

import `in`.conscent.mylibrary.adapter.SearchAdapter
import `in`.conscent.mylibrary.databinding.ActivitySearchBinding
import `in`.conscent.mylibrary.extensions.CustomSpinnerAdapter
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.viewmodel.MainViewModel
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    //declare views variable
    private var binding: ActivitySearchBinding? = null
    private var recyclerView: RecyclerView? = null
    private var spinner: Spinner? = null

    //create object for RecyclerView adapter
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val mainViewModel: MainViewModel? by viewModels()

    //variable needed in case of paginated api response
    private var currentPage = 1
    private var isCurrentlyLoading: Boolean = true
    private var totalPageCount = 10//no value received in api as totalPageCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initializeViews()
        setUpObservers(this)
        makeApiRequest()
    }

    private fun initializeViews() {
        spinner = binding?.spinnerAndroid
        recyclerView = binding?.searchResultRecyclerView
        recyclerView?.apply {
            adapter = searchAdapter
            layoutManager = linearLayoutManager
        }
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
    }

    private fun makeApiRequest() {
        lifecycleScope.launch {
            val awaitedResponseOfCategories = async { mainViewModel?.getCategoriesResponse() }
            val awaitedResponseOfSearch = async { getSearchResponse(currentPage) }
            println(awaitedResponseOfCategories)
            println(awaitedResponseOfSearch)
        }
    }

    private fun getSearchResponse(page: Int) {
        mainViewModel?.getSearchResponse(page)
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
