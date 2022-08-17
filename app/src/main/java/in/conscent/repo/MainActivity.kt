package `in`.conscent.repo

import `in`.conscent.mylibrary.CatBreedListActivity
import `in`.conscent.mylibrary.adapter.BreedListAdapter
import `in`.conscent.mylibrary.disclose.AppConnectorImp
import `in`.conscent.mylibrary.extensions.PaginationScrollListener
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.repo.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private val searchAdapter: BreedListAdapter by lazy { BreedListAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private var recyclerView: RecyclerView? = null

    //variable needed in case of paginated api response
    private var currentPage = 1
    private var isCurrentlyLoading: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpInitialViews()
        setUpClickListeners()
        setupMethodsForDataExchange()
    }

    private fun setUpInitialViews() {
        recyclerView = binding?.searchResultRecyclerView
        recyclerView?.apply {
            adapter = searchAdapter
            layoutManager = linearLayoutManager
        }
        attachCustomScrollListener()
    }

    private fun setUpClickListeners() {
        binding?.navigateApp1?.setOnClickListener {
            val intent = Intent(this, CatBreedListActivity::class.java)
            startActivity(intent)
        }
    }
    //initially it will be called
    private fun setupMethodsForDataExchange() {
        appConnectorImp.attachObserverForServerData(this)
        appConnectorImp.getCategoriesData()
        requestForBreedList()
    }


    private fun requestForBreedList(){
        showHidePgBar(true)
        appConnectorImp.getSearchData(currentPage)
    }

    override fun onDestroy() {
        super.onDestroy()
        appConnectorImp.destroyViewModels()
    }

    private fun attachCustomScrollListener() {
        val paginationScrollListener = object : PaginationScrollListener(layoutManager = linearLayoutManager) {
            override fun loadMoreItems() {
                isCurrentlyLoading = true
                currentPage += 1
                requestForBreedList()
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

    private val appConnectorImp: AppConnectorImp = object : AppConnectorImp(WeakReference(this)) {
        override fun postSearchData(arrayList: ArrayList<SearchResponseModel>?) {
            isCurrentlyLoading = false;
            showHidePgBar(false)
            if (!arrayList.isNullOrEmpty()) {
                searchAdapter.submitList(arrayList)
            }else{
                isCurrentlyLoading=true
            }
        }
        override fun postCategoryData(categoryResponse: CategoryResponse?) {
            Toast.makeText(this@MainActivity, "Category Data will received", Toast.LENGTH_LONG).show()
        }
    }
}