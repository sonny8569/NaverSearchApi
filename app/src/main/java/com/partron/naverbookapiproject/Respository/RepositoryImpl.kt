package com.partron.naverbookapiproject.Respository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Https.RetrofitInterface
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.Utill.Define
import com.partron.naverbookapiproject.Utill.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(): Repository {
    private val logTag = "RepositoryImpl"
    private val bookLiveData = MutableLiveData<Resource<BookResponse>>()
    private val saveSearchListLiveData = MutableLiveData<Resource<String>>()


    //책 검색 기록 request 함수
    override suspend fun requestSaveSearchList(data: SearchList) {
        saveSearchListLiveData.postValue(Resource.loading())
        try {
            val searchList = AppdataBase.getInstance().dataDao().getSearchList()
            if (searchList.contains(data)) {
                AppdataBase.getInstance().dataDao().updateSearchList(data)
                saveSearchListLiveData.postValue(Resource.success("Success"))
                return
            }
            AppdataBase.getInstance().dataDao().insertSearchList(data)
            saveSearchListLiveData.postValue(Resource.success("Success"))
        } catch (e: java.lang.Exception) {
            saveSearchListLiveData.postValue(Resource.error(e))
        }
    }
    override fun getSaveSearchListStatus(): MutableLiveData<Resource<String>>  = saveSearchListLiveData

    //책 목록 request 함수
    override suspend fun requestBookApi(id: String, pw: String, query: String) {
        bookLiveData.postValue(Resource.loading())
        val requestBook = RetrofitInterface.requestRetrofit().requestBookApi(id, pw, query)
        requestBook.enqueue(object : Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                when(response.code()){
                    Define.SYSTEM_ERROR_NO_API ->{
                        Log.d(logTag , "No API Please Check Url")
                        bookLiveData.postValue(Resource.fail(Define.MESSAGE_SYSTEM_ERROR))
                    }
                      Define.SYSTEM_ERROR_WRONG_QUERY ->{
                          bookLiveData.postValue(Resource.fail(Define.MESSAGE_NO_QUERY))
                    }
                    Define.SYSTEM_ERROR ->{
                        bookLiveData.postValue(Resource.fail(Define.MESSAGE_SYSTEM_ERROR))
                    }
                    Define.SYSTEM_SUCCESS ->{
                        val data = response.body()
                        if(data == null){
                            bookLiveData.postValue(Resource.fail(Define.MESSAGE_DATA_NULL))
                            return
                        }
                        bookLiveData.postValue(Resource.success(data))
                    }
                    else ->{
                        bookLiveData.postValue(Resource.error(null))
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                bookLiveData.postValue(Resource.error(t))
            }

        })
    }

    override fun getBookLiveData(): MutableLiveData<Resource<BookResponse>> = bookLiveData


}