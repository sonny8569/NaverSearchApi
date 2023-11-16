package com.partron.naverbookapiproject.search.repo

import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.search.useCaseObject.BookUseCase

interface Repository {
    //책 API
    suspend fun requestBookApi(id : String , pw : String , query : String) : BookUseCase
    //척 검색 기록 api
    suspend fun requestSaveSearchList(data : SearchList)

}