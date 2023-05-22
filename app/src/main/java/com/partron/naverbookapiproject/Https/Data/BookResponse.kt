package com.partron.naverbookapiproject.Https.Data

data class BookResponse (
    val items: ArrayList<Book>,
    val total: Int,
    val start: Int,
    val display: Int
)