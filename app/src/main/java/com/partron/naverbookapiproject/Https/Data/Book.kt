package com.partron.naverbookapiproject.Https.Data

data class Book(
    val title: String,
    val link: String,
    val image: String,
    val director: String,
    val actor: String,
    val userRating: Double,
    val discount : String
)
