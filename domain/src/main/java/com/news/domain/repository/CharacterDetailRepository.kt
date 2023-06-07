package com.news.domain.repository

import com.news.domain.model.CharacterDetail
import com.news.domain.util.StatusRequest

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(id: Int): StatusRequest<CharacterDetail>
}