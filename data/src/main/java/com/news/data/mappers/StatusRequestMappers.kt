package com.news.data.mappers

import com.news.data.models.characters.CharacterDto
import com.news.data.models.characters.CharactersDto
import com.news.data.models.characters.singleCharacter.CharacterDetailDto
import com.news.domain.model.ApiError
import com.news.domain.model.Character
import com.news.domain.model.CharacterDetail
import com.news.domain.util.StatusRequest

fun StatusRequest<CharactersDto>.toStatusRequestCharacters(): StatusRequest<List<Character>> =
    try {
        StatusRequest(
            this.status,
            this.data?.toCharactersList(),
            this.apiError
        )
    }catch(e: Exception) {
        statusRequestMapperException(e, "StatusRequest<Characters> mapper exception")
    }

fun StatusRequest<List<CharacterDto>>.toStatusRequestCharactersList(): StatusRequest<List<Character>> =
    try {
        StatusRequest(
            this.status,
            this.data?.map { it.toCharactersList() },
            this.apiError
        )
    }catch(e: Exception) {
        statusRequestMapperException(e, "StatusRequest<List<Characters>> mapper exception")
    }

fun StatusRequest<CharacterDetailDto>.toStatusRequestCharacterDetail(): StatusRequest<CharacterDetail> =
    try {
        StatusRequest(
            this.status,
            this.data?.toCharacterDetail(),
            this.apiError
        )
    }catch(e: Exception) {
        statusRequestMapperException(e, "StatusRequest<List<Characters>> mapper exception")
    }


private fun <T> statusRequestMapperException(e: Exception, exceptionMessage: String): StatusRequest<T> =
    StatusRequest(
        status = StatusRequest.Status.ERROR,
        apiError = ApiError(message = e.message ?: exceptionMessage)
    )