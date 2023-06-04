package com.partron.naverbookapiproject.Respository

import androidx.lifecycle.MutableLiveData
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Https.RetrofitInterface
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.Utill.Resource

interface Repository {
    //책 API
//    fun requestBookApi(id : String , pw : String , query : String) = RetrofitInterface.requestRetrofit()
//        .requestBookApi(id , pw , query )
    suspend fun requestBookApi(id : String , pw : String , query : String)
    fun getBookLiveData() : MutableLiveData<Resource<BookResponse>>

    //척 검색 기록 api
//    fun requestSaveSearchList(data : SearchList) = AppdataBase.getInstance().dataDao().insertSearchList(data)
    suspend fun requestSaveSearchList(data : SearchList)
    fun getSaveSearchListStatus() : MutableLiveData<Resource<String>>

}