package com.partron.naverbookapiproject.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Respository.Repository
import com.partron.naverbookapiproject.Respository.RepositoryImpl
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.Utill.CompanionFunction
import com.partron.naverbookapiproject.Utill.Define
import com.partron.naverbookapiproject.Utill.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class SearchViewModel ( private val repository : RepositoryImpl) : ViewModel() {

    private var _bookSearchLiveData = repository.getBookLiveData()
    val bookSearchLiveData get() = _bookSearchLiveData

    private var TAG = "SearchViewModel"
//    private val test = LiveData<>()

    /**
     * 책 검색 api
     */
    fun requestBookApi(id: String, pw: String, query: String) =viewModelScope.launch(Dispatchers.IO) {
        _bookSearchLiveData.postValue(Resource.loading())

        repository.requestBookApi(id, pw, query)
//        _bookSearchLiveData.postValue(repository.getBookLiveData().value)
//        _bookSearchLiveData.value = repository.getBookLiveData().value
    }

    fun requestSaveSearchList(query :String) = viewModelScope.launch(Dispatchers.IO) {
        val time = CompanionFunction.getCurrentDateTime()
        val data = SearchList(time , query)
        repository.requestSaveSearchList(data)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG , "The SearchViewModel is Clear")
    }
}