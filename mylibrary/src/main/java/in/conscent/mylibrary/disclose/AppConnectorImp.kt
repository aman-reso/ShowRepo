package `in`.conscent.mylibrary.disclose

import `in`.conscent.mylibrary.models.CategoryResponse
import `in`.conscent.mylibrary.models.SearchResponseModel
import `in`.conscent.mylibrary.viewmodel.MainViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

//a helper class which allows to communicate with other modules
//uses viewModel show need
abstract class AppConnectorImp( activity: WeakReference<AppCompatActivity?>?) {
    private val mainViewModel: MainViewModel? by  activity?.get()!!.viewModels()

    fun attachObserverForServerData(activity: AppCompatActivity) {
        mainViewModel?.apply {
            searchLiveData.observe(activity) {
                postSearchData(it)
            }
            categoryLiveData.observe(activity) {
                postCategoryData(it)
            }
        }
    }

    fun getSearchData(pageNum: Int? = 1) {
        if (pageNum != null) {
            mainViewModel?.getSearchResponse(pageNum)
        }
    }

    fun getCategoriesData() {
        mainViewModel?.getCategoriesResponse()
    }

    abstract fun postSearchData(arrayList: ArrayList<SearchResponseModel>?)
    abstract fun postCategoryData(categoryResponse: CategoryResponse?)

    fun destroyViewModels() {
        mainViewModel?.clearViewModel()
    }
}