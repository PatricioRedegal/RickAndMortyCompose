package com.news.domain.repository

import com.news.domain.model.Character
import com.news.domain.util.StatusRequest

interface CharactersRepository {
    suspend fun getCharacters(page: Int? = 1, name: String? = null, status: String? = null): StatusRequest<List<Character>>
    suspend fun getFavoriteCharacters(ids: List<Int>): StatusRequest<List<Character>>
}