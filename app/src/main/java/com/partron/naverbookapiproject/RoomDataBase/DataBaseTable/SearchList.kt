package com.partron.naverbookapiproject.RoomDataBase.DataBaseTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase

@Entity(
    tableName = AppdataBase.TABLE_SEARCH_LIST
)
data class SearchList(
    @PrimaryKey var time : String ,
    @ColumnInfo var search : String
)
