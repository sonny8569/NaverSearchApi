package com.partron.naverbookapiproject.Utill

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CompanionFunction {
    companion object{
        fun getCurrentDateTime(): String {
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            return currentDateTime.format(formatter)
        }
    }
}