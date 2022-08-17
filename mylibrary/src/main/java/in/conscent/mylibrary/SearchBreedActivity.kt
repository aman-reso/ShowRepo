package `in`.conscent.mylibrary

import `in`.conscent.mylibrary.adapter.BreedSearchAdapter
import `in`.conscent.mylibrary.constants.AppConstants.DEBOUNCE_TIME_FOR_SEARCH
import `in`.conscent.mylibrary.databinding.BreedSearchActivityBinding
import `in`.conscent.mylibrary.extensions.customTextChangesListener
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.viewmodel.MainViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class SearchBreedActivity : AppCompatActivity() {
    private var binding: BreedSearchActivityBinding? = null
    private var recyclerView: RecyclerView? = null
    private var searchEditText: EditText? = null
    private val searchAdapter: BreedSearchAdapter by lazy { BreedSearchAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.breed_search_activity)
        initializeViews()
        setUpRecyclerView()
        setUpSearchListeners()
    }

    private fun initializeViews() {
        recyclerView = binding?.searchResultRecyclerView
        searchEditText = binding?.searchEditText
    }

    private fun setUpRecyclerView() {
        recyclerView?.apply {
            adapter = searchAdapter
            layoutManager = linearLayoutManager
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun setUpSearchListeners() {
        binding?.searchEditText?.customTextChangesListener()?.filterNot { it.isNullOrBlank() }
            ?.debounce(DEBOUNCE_TIME_FOR_SEARCH)
            ?.flatMapLatest {
                showHidePgBar(true)
                executeSearch(term = it.toString())
            }
            ?.onEach { it ->
                showHidePgBar(false)
                if (it?.isEmpty() == true) {
                    Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show()
                }
                if (it != null) {
                    submitSearchListToRecyclerView(it)
                }
            }?.launchIn(lifecycleScope)
    }

    private suspend fun executeSearch(term: String): Flow<ArrayList<SearchResponseModel>?> = mainViewModel.executeApiForSearchBreed(term)


    private fun submitSearchListToRecyclerView(arrayList: ArrayList<SearchResponseModel>) {
        searchAdapter.submitList(arrayList)
    }

    private fun showHidePgBar(canShow: Boolean) {
        if (canShow) {
            binding?.searchProgressbar?.visibility = View.VISIBLE
        } else {
            binding?.searchProgressbar?.visibility = View.GONE
        }
    }
}