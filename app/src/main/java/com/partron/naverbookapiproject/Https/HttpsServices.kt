package com.partron.naverbookapiproject.Https

import com.partron.naverbookapiproject.Https.Data.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HttpsServices {

    /**
     * 책 요청 api
     */
    @GET("book.json")
    fun requestBookApi(
        @Header("X-Naver-Client-Id")id : String ,
        @Header("X-Naver-Client-Secret")pw : String,
        @Query("query")query : String,
  ) : Call<BookResponse>

}