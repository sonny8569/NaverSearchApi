package com.partron.naverbookapiproject.search.useCase

import com.partron.naverbookapiproject.Https.Data.BookResponse
import com.partron.naverbookapiproject.search.repo.Repository
import com.partron.naverbookapiproject.utill.UseCase
import javax.inject.Inject

class RequestBookUseCase @Inject constructor(private val repo : Repository) : UseCase<RequestBookUseCase.PARAM , RequestBookUseCase.BookResult>{



    data class PARAM(val id : String , val pw : String , val query : String) : UseCase.Param

    sealed interface BookResult : UseCase.Result {
        data class Success(val data : BookResponse) : BookResult
        data class Fail(val message : String) : BookResult
    }

    override suspend fun invoke(param: PARAM): BookResult {
        val result = repo.requestBookApi(param.id , param.pw , param.query)
        if(result)
    }
}