package com.partron.naverbookapiproject.RoomDataBase.DataBaseTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase

@Entity(
    tableName = AppdataBase.TABLE_FAVORITE_BOOK
)

data class FavoriteBookList(
    @PrimaryKey var time  : Long,
    @ColumnInfo var title : String,
    @ColumnInfo var image : String,
    @ColumnInfo var link : String
)
