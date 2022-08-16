package `in`.conscent.repo

import `in`.conscent.mylibrary.SearchActivity
import `in`.conscent.mylibrary.adapter.SearchAdapter
import `in`.conscent.mylibrary.disclose.AppConnectorImp
import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.repo.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
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

    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private var recyclerView: RecyclerView? = null

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
    }

    private fun setUpClickListeners() {
        binding?.navigateApp1?.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupMethodsForDataExchange() {
        appConnectorImp.attachObserverForServerData(this)
        appConnectorImp.getCategoriesData()
        appConnectorImp.getSearchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        appConnectorImp.destroyViewModels()
    }

    private val appConnectorImp: AppConnectorImp = object : AppConnectorImp(WeakReference(this)) {
        override fun postSearchData(arrayList: ArrayList<SearchResponseModel>?) {
            if (arrayList != null) {
                searchAdapter.submitList(arrayList)
            }
        }
        override fun postCategoryData(categoryResponse: CategoryResponse?) {
            Toast.makeText(this@MainActivity, "received category", Toast.LENGTH_LONG).show()
        }
    }
}