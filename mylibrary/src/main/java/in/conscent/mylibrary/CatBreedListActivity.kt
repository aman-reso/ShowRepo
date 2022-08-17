package `in`.conscent.mylibrary

import `in`.conscent.mylibrary.adapter.BreedListAdapter
import `in`.conscent.mylibrary.databinding.BreedListActivityBinding
import `in`.conscent.mylibrary.extensions.CustomSpinnerAdapter
import `in`.conscent.mylibrary.extensions.PaginationScrollListener
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.viewmodel.MainViewModel
import android.content.Intent
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
class CatBreedListActivity : AppCompatActivity(), View.OnClickListener {
    //declare views variable
    private var binding: BreedListActivityBinding? = null
    private var recyclerView: RecyclerView? = null
    private var spinner: Spinner? = null

    //create object for RecyclerView adapter
    private val searchAdapter: BreedListAdapter by lazy { BreedListAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val mainViewModel: MainViewModel? by viewModels()

    //variable needed in case of paginated api response
    private var currentPage = 1
    private var isCurrentlyLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.breed_list_activity)
        initializeViews()
        setUpClickListeners()
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
        attachCustomScrollListener()
    }

    private fun setUpClickListeners() {
        binding?.redirections?.setOnClickListener(this)
    }

    private fun createSpinner(categoryResponse: CategoryResponse) {
        val userSpinnerAdapter = CustomSpinnerAdapter(this, R.layout.custom_spinner_item, categoryResponse)
        spinner?.adapter = userSpinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.selectedItem as CategoryResponse.CategoryResponseItem
                Toast.makeText(this@CatBreedListActivity, selectedItem.name, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                println(p0)
            }
        }
    }

    private fun setUpObservers(searchActivity: CatBreedListActivity) {
        mainViewModel?.apply {
            this.breedListLiveData.observe(searchActivity) {
                isCurrentlyLoading = false
                showHidePgBar(false)
                if (!it.isNullOrEmpty()) {
                    searchAdapter.submitList(it)
                } else {
                    isCurrentlyLoading = true
                }
            }
            this.categoryLiveData.observe(this@CatBreedListActivity) {
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
        showHidePgBar(true)
        mainViewModel?.getSearchResponse(page)
    }

    private fun attachCustomScrollListener() {
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

    private fun showHidePgBar(canShow: Boolean) {
        if (canShow) {
            binding?.searchProgressbar?.visibility = View.VISIBLE
        } else {
            binding?.searchProgressbar?.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.redirections -> {
                val intent = Intent(this, SearchBreedActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
