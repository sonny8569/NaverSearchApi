package com.partron.naverbookapiproject.Utill

/**
 * A generic class that holds a value with its loading status.
 * 롤벡시 title 삭제
 * @see <a href="https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt">Sample apps for Android Architecture Components</a>
 */
data class Resource<out T>(val status: Status, val data: T?, val exception: Throwable?, val newPage: Int?, val FailMessage:String?, val title: String? , val message: String?) {

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
        FaIL
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data,null, null , null,null , null )
        }

        fun <T> error(exception: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, exception, null, null,null , null )
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null, null, null,null, null )
        }
        fun <T> fail(message : String) : Resource<T>{
            return Resource(Status.FaIL, null, null, null, null,null, message )
        }

    }
}