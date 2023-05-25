package com.partron.naverbookapiproject.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.Https.Repository
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.SearchList
import com.partron.naverbookapiproject.Utill.CompanionFunction
import com.partron.naverbookapiproject.Utill.Define
import com.partron.naverbookapiproject.Utill.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class SearchViewModel ( private val repository : Repository) : ViewModel() {

    private val _bookSearchLiveData = MutableLiveData<Resource<ArrayList<Book>>>()
    val bookSearchLiveData get() = _bookSearchLiveData

//    private val test = LiveData<>()
    /**
     * 책 검색 api
     */
    fun requestBookApi(id :String , pw : String , query : String) = viewModelScope.launch(Dispatchers.IO) {
        _bookSearchLiveData.postValue(Resource.loading())

        val requestBookApi = repository.requestBookApi(id ,pw , query)
        requestBookApi.enqueue(object: Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if(response.isSuccessful){ // 200 404 401
                    when(response.code()){
                        Define.SYSTEM_ERROR_NO_API ->{
                            _bookSearchLiveData.postValue(Resource.error(null))
                            return
                        }
                        Define.SYSTEM_ERROR_WRONG_QUERY ->{
                            _bookSearchLiveData.postValue(Resource.fail(Define.MESSAGE_NO_QUERY))
                            return
                        }
                        Define.SYSTEM_ERROR ->{
                            _bookSearchLiveData.postValue(Resource.fail(Define.MESSAGE_SYSTEM_ERROR))
                        }
                        Define.SYSTEM_SUCCESS->{
                            val data = response.body()
                            if(data == null){
                                _bookSearchLiveData.postValue(Resource.fail(Define.MESSAGE_DATA_NULL))
                                return
                            }
                            val result = data.items
                            val saveStatus  = requestSaveSearchList(query)
                            if(!saveStatus){
                                _bookSearchLiveData.postValue(Resource.error(null))
                                return
                            }
                            _bookSearchLiveData.postValue(Resource.success(result))
                        }
                    }
                    return
                }
                _bookSearchLiveData.postValue(Resource.error(null))
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                _bookSearchLiveData.postValue(Resource.error(t))
            }
        })
    }
    @Synchronized
    fun requestSaveSearchList(query :String): Boolean {
        val time = CompanionFunction.getCurrentDateTime()
        val data = SearchList(time , query)
        try {
            repository.requestSaveSearchList(data)
        }catch (E: java.lang.Exception){
            return false
        }
        return true
    }
//    fun requestBookApi(id :String , pw : String , query : String) = viewModelScope.launch(Dispatchers.IO) {
//        main(id, pw, query)
//    }

    fun main(id :String , pw : String , query : String) {
        val clientId = id // 애플리케이션 클라이언트 아이디
        val clientSecret = pw // 애플리케이션 클라이언트 시크릿

        var text: String? = null
        try {
            text = URLEncoder.encode(query, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("검색어 인코딩 실패", e)
        }

        val apiURL = "https://openapi.naver.com/v1/search/book.json?query=$text" // JSON 결과
        // val apiURL = "https://openapi.naver.com/v1/search/blog.xml?query=$text" // XML 결과

        val requestHeaders: MutableMap<String, String> = HashMap()
        requestHeaders["X-Naver-Client-Id"] = clientId
        requestHeaders["X-Naver-Client-Secret"] = clientSecret
        val responseBody = get(apiURL, requestHeaders)

        println(responseBody)
    }

    private fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
        val con = connect(apiUrl)
        return try {
            con.requestMethod = "GET"
            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                readBody(con.inputStream)
            } else { // 오류 발생
                readBody(con.errorStream)
            }
        } catch (e: IOException) {
            throw RuntimeException("API 요청과 응답 실패", e)
        } finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        return try {
            val url = URL(apiUrl)
            url.openConnection() as HttpURLConnection
        } catch (e: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
        } catch (e: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl", e)
        }
    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        return BufferedReader(streamReader).use { lineReader ->
            val responseBody = StringBuilder()
            var line: String?
            while (lineReader.readLine().also { line = it } != null) {
                responseBody.append(line)
            }
            responseBody.toString()
        }
    }


}