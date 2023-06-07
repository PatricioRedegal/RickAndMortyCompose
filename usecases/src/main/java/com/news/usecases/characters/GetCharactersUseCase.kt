package com.news.usecases.characters

import com.news.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(page: Int? = 1, name: String? = null, status: String? = null) =
        charactersRepository.getCharacters(page, name, status)
}


