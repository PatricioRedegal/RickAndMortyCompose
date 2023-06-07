package com.news.data.models.characters

data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val status: String,
    val species:String,
    val origin:Origin,
)