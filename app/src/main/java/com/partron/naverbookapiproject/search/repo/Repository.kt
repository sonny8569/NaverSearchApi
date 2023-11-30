package com.partron.naverbookapiproject.search.repo

import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.FavoriteBookList
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.search.useCaseObject.BookUseCase

interface Repository {
    //책 API
    suspend fun requestBookApi(id : String , pw : String , query : String) : BookUseCase
    // 책 저장 함수
    suspend fun requestSaveBook(book : FavoriteBookList) : Boolean
}