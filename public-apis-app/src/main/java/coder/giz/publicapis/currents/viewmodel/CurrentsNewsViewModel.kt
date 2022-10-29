package coder.giz.publicapis.currents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coder.giz.android.yfutility.data.toJson
import coder.giz.publicapis.currents.model.News
import coder.giz.publicapis.currents.network.CurrentsNewsRepository
import coder.giz.publicapis.helper.logW
import kotlinx.coroutines.launch

/**
 * Created by GizFei on 2022/10/29
 */
class CurrentsNewsViewModel : ViewModel() {

    private val mRepository = CurrentsNewsRepository()

    private val mNewsListLiveData = MutableLiveData<List<News>>()
    val newsListLiveData: LiveData<List<News>> = mNewsListLiveData

    private val mLoadingLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean> = mLoadingLiveData

    private val mSnackbarMsgLiveData = MutableLiveData<String?>()
    val snackbarMsgLiveData: LiveData<String?> = mSnackbarMsgLiveData

    fun getLatestNews() {
        viewModelScope.launch {
            try {
                mLoadingLiveData.value = true
                val news = mRepository.getLatestNews()
                logW {
                    "News: ${news.toJson()}"
                }
                mNewsListLiveData.value = news
            } catch (e: Exception) {
                mSnackbarMsgLiveData.value = e.message
            } finally {
                mLoadingLiveData.value = false
            }
        }
    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        mSnackbarMsgLiveData.value = null
    }

}