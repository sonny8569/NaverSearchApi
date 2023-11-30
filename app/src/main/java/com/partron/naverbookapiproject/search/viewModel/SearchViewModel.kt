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
import com.partron.naverbookapiproject.search.useCase.RequestSaveBookUseCase

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookUseCase: RequestBookUseCase,
    private val saveBookUseCase: RequestSaveBookUseCase
) : ViewModel() {

    private var _liveData = MutableLiveData<SearchViewResult>()
    val liveData: LiveData<SearchViewResult> get() = _liveData

    private var TAG = "SearchViewModel"

    /**
     * 책 검색 api
     */
    fun requestBookApi(id: String, pw: String, query: String) {
        _liveData.value = SearchViewResult.Loading("loading for get Book")
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = bookUseCase.invoke(RequestBookUseCase.PARAM(id, pw, query))) {
                is RequestBookUseCase.BookResult.Fail -> {
                    _liveData.postValue(SearchViewResult.ERROR(result.message))
                }

                is RequestBookUseCase.BookResult.Success -> {
                    _liveData.postValue(SearchViewResult.BookResponse(result.data))
                }
            }
        }
    }

    fun saveBook(data: Book) {
        _liveData.value = SearchViewResult.Loading("loading for save Book")
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = saveBookUseCase.invoke(RequestSaveBookUseCase.PARAM(data))) {
                is RequestSaveBookUseCase.SaveBookResult.Fail -> {
                    _liveData.postValue(SearchViewResult.ERROR(result.message))
                }

                is RequestSaveBookUseCase.SaveBookResult.Success -> {
                    _liveData.postValue(SearchViewResult.SaveBookResult(true))
                }
            }
        }
    }

    sealed class SearchViewResult {
        data class Loading(val message: String) : SearchViewResult()
        data class BookResponse(val data: ArrayList<Book>) : SearchViewResult()

        data class SaveBookResult(val result: Boolean) : SearchViewResult()
        data class ERROR(val message: String) : SearchViewResult()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "The SearchViewModel is Clear")
    }
}