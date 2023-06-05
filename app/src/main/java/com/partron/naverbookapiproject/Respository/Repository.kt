package com.partron.naverbookapiproject.Respository

import androidx.lifecycle.MutableLiveData
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Https.RetrofitInterface
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.Utill.Resource

interface Repository {
    //책 API
    suspend fun requestBookApi(id : String , pw : String , query : String)
    fun getBookLiveData() : MutableLiveData<Resource<ArrayList<Book>>>

    //척 검색 기록 api
    suspend fun requestSaveSearchList(data : SearchList)
    fun getSaveSearchListStatus() : MutableLiveData<Resource<String>>

}