package com.partron.naverbookapiproject.RoomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.FavoriteBookList
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList

@Dao
interface DataDao {

    @Insert
    fun insertSearchList(data : SearchList)

    @Insert
    fun insertFavoriteBook(data : FavoriteBookList)

    @Query("SELECT * FROM ${AppdataBase.TABLE_SEARCH_LIST}")
    fun getSearchList() : List<SearchList>

    @Query("SELECT * FROM ${AppdataBase.TABLE_FAVORITE_BOOK}")
    fun getFavoriteBookList() : List<FavoriteBookList>

}