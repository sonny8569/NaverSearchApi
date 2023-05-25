package com.partron.naverbookapiproject.RoomDataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.partron.naverbookapiproject.MainApplication
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.FavoriteBookList
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList

@Database(
    entities = [
        FavoriteBookList :: class ,
        SearchList :: class
    ],
    version = 1
)
abstract class AppdataBase : RoomDatabase() {
    abstract fun dataDao () : DataDao

    companion object{
        const val TABLE_SEARCH_LIST = "searchList"
        const val TABLE_FAVORITE_BOOK = "favoriteBook"
        @Volatile
        private var instance : AppdataBase ? = null

        @JvmStatic
        fun getInstance() : AppdataBase{
            instance ?: synchronized(this){
                instance = Room.databaseBuilder(MainApplication.appContext ,
                    AppdataBase :: class.java,
                    "database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }
}