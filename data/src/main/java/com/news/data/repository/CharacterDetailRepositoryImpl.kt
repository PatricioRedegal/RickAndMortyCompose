package com.news.data.repository

import com.news.data.api.ApiService
import com.news.data.mappers.toStatusRequestCharacterDetail
import com.news.data.remote.BaseDataSource
import com.news.domain.model.CharacterDetail
import com.news.domain.repository.CharacterDetailRepository
import com.news.domain.util.StatusRequest
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CharacterDetailRepository, BaseDataSource() {
    override suspend fun getCharacterDetail(id: Int): StatusRequest<CharacterDetail> =
        getResult {
            apiService.getCharacterDetails(id)
        }.toStatusRequestCharacterDetail()
}

