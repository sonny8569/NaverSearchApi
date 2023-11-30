package com.partron.naverbookapiproject.search.useCase

import com.partron.naverbookapiproject.Https.Data.Book
import com.partron.naverbookapiproject.RoomDataBase.DataBaseTable.FavoriteBookList
import com.partron.naverbookapiproject.search.repo.Repository
import com.partron.naverbookapiproject.utill.Define
import com.partron.naverbookapiproject.utill.UseCase
import javax.inject.Inject

class RequestSaveBookUseCase @Inject constructor(private val repo: Repository) :
    UseCase<RequestSaveBookUseCase.PARAM, RequestSaveBookUseCase.SaveBookResult> {
    data class PARAM(val data: Book) : UseCase.Param
    sealed interface SaveBookResult : UseCase.Result {
        data class Success(val book: Book) : SaveBookResult
        data class Fail(val message: String) : SaveBookResult
    }

    override suspend fun invoke(param: PARAM): SaveBookResult {
        val saveData = makeSaveObject(param.data)
        val result = repo.requestSaveBook(saveData)
        if (!result) {
            return SaveBookResult.Fail(Define.MESSAGE_SYSTEM_ERROR)
        }
        return SaveBookResult.Success(param.data)
    }

    private fun makeSaveObject(data: Book): FavoriteBookList {
        val time = System.currentTimeMillis()
        return FavoriteBookList(time, data.title, data.image, data.link)
    }
}