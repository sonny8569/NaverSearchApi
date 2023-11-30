package com.partron.naverbookapiproject.search.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Https.RetrofitInterface
import com.partron.naverbookapiproject.RoomDataBase.AppdataBase
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.FavoriteBookList
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.search.useCaseObject.BookUseCase
import com.partron.naverbookapiproject.utill.Define
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

class RepositoryImpl @Inject constructor() : Repository {
    private val logTag = "RepositoryImpl"

    //책 검색 기록 request 함수

    //책 목록 request 함수
    override suspend fun requestBookApi(id: String, pw: String, query: String): BookUseCase {
        val requestBook = RetrofitInterface.requestRetrofit().requestBookApi(id, pw, query)
        val requestBookBackground = CoroutineScope(Dispatchers.IO).async {
            requestBook.awaitResponse()
        }
        val response = requestBookBackground.await()

        return when (response.code()) {
            Define.SYSTEM_ERROR_NO_API -> {
                Log.d(logTag, "No API Please check Url")
                BookUseCase(Define.MESSAGE_SYSTEM_ERROR, null)
            }

            Define.SYSTEM_ERROR_WRONG_QUERY -> {
                Log.d(logTag, "Wrong query")
                BookUseCase(Define.MESSAGE_NO_QUERY, null)
            }

            Define.SYSTEM_SUCCESS -> {
                val data = response.body() ?: return BookUseCase(Define.MESSAGE_DATA_NULL, null)
                BookUseCase(Define.MESSAGE_SUCCESS, data)
            }

            else -> BookUseCase(Define.MESSAGE_SYSTEM_ERROR, null)
        }
    }

    override suspend fun requestSaveBook(book: FavoriteBookList): Boolean {
        return try{
            AppdataBase.getInstance().dataDao().insertFavoriteBook(book)
            true
        }catch (e: Exception){
            false
        }
    }

}