package com.partron.naverbookapiproject.search.repo

import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList

interface Repository {
    //책 API
    suspend fun requestBookApi(id : String , pw : String , query : String)

    //척 검색 기록 api
    suspend fun requestSaveSearchList(data : SearchList)

}