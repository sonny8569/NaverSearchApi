package com.partron.naverbookapiproject.search.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partron.naverbookapiproject.search.useCase.RequestBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.partron.naverbookapiproject.Https.Data.Book

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookUseCase: RequestBookUseCase
) : ViewModel() {

    private var _bookSearchLiveData = MutableLiveData<SearchViewResult>()
    val bookSearchLiveData: LiveData<SearchViewResult> get() = _bookSearchLiveData

    private var TAG = "SearchViewModel"

    /**
     * 책 검색 api
     */
    fun requestBookApi(id: String, pw: String, query: String) {
        _bookSearchLiveData.value = SearchViewResult.Loading("loading for get Book")
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = bookUseCase.invoke(RequestBookUseCase.PARAM(id, pw, query))) {
                is RequestBookUseCase.BookResult.Fail -> {
                    _bookSearchLiveData.postValue(SearchViewResult.ERROR(result.message))
                }
                is RequestBookUseCase.BookResult.Success -> {
                    _bookSearchLiveData.postValue(SearchViewResult.BookResponse(result.data))
                }
            }
        }
    }


    sealed class SearchViewResult {
        data class Loading(val message: String) : SearchViewResult()
        data class BookResponse(val data: ArrayList<Book>) : SearchViewResult()
        data class ERROR(val message: String) : SearchViewResult()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The SearchViewModel is Clear")
    }
}