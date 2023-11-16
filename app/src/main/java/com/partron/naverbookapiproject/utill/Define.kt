package com.partron.naverbookapiproject.utill

class Define {
    companion object{
        //SYSTEM RESPONSE
        const val SYSTEM_ERROR_WRONG_QUERY = 400
        const val SYSTEM_ERROR_NO_API = 404
        const val SYSTEM_ERROR = 500
        const val SYSTEM_SUCCESS = 200

        //Message
        const val MESSAGE_NO_QUERY = "책 재목을 한번 확인해주세요 "
        const val MESSAGE_SYSTEM_ERROR= "시스템 에러 "
        const val MESSAGE_DATA_NULL = "관련된 책이 없습니다"

    }
}