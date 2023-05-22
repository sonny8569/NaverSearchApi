package com.partron.naverbookapiproject.Https

class Repository {
    //책 API
    fun requestBookApi(id : String , pw : String , query : String) = RetrofitInterface.requestRetrofit().requestBookApi(id , pw , query )

}