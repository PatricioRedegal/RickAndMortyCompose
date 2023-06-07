package com.news.data.repository

import com.news.data.api.ApiService
import com.news.data.mappers.toStatusRequestCharacters
import com.news.data.mappers.toStatusRequestCharactersList
import com.news.data.remote.BaseDataSource
import com.news.domain.repository.CharactersRepository
import com.news.domain.util.StatusRequest
import com.news.domain.model.Character
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CharactersRepository, BaseDataSource() {
    override suspend fun getCharacters(page: Int?, name: String?, status: String?): StatusRequest<List<Character>> = getResult {
            apiService.getCharacters(page, name, status)
        }.toStatusRequestCharacters()

    override suspend fun getFavoriteCharacters(ids: List<Int>): StatusRequest<List<Character>> =
        getResult {
            apiService.getCharactersById(ids.joinToString(separator = ",", postfix = ","))
        }.toStatusRequestCharactersList()
}