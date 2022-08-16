package `in`.conscent.mylibrary.viewmodel

import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.models.GlobalNetResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.repository.NetworkRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private var io_dispatcher = Dispatchers.IO

@HiltViewModel
internal class MainViewModel @Inject constructor(private var repository: NetworkRepository) : ViewModel() {
    internal var searchLiveData = MutableLiveData<ArrayList<SearchResponseModel>?>()
    internal var categoryLiveData = MutableLiveData<CategoryResponse?>()

    internal  fun getSearchResponse(page: Int) {
        viewModelScope.launch(io_dispatcher) {
            when (val searchResponseModel = repository.getSearchData(page)) {
                is GlobalNetResponse.Success -> {
                    searchLiveData.postValue(searchResponseModel.value)
                }
                else -> {
                    searchLiveData.postValue(null)
                }
            }
        }
    }

    internal fun getCategoriesResponse() {
        viewModelScope.launch(io_dispatcher) {
            when (val categoriesResponse = repository.getCategoriesData()) {
                is GlobalNetResponse.Success -> {
                    categoryLiveData.postValue(categoriesResponse.value)
                }
                else -> {
                    categoryLiveData.postValue(null)
                }
            }
        }
    }

    internal fun clearViewModel(){
        onCleared()
    }
}

