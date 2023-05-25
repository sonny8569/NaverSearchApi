package com.partron.naverbookapiproject.Https

import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList

class Repository {
    //책 API
    fun requestBookApi(id : String , pw : String , query : String) = RetrofitInterface.requestRetrofit().requestBookApi(id , pw , query )

    //척 검색 기록 api
    fun requestSaveSearchList(data : SearchList) = AppdataBase.getInstance().dataDao().insertSearchList(data)
}