package com.news.usecases.characters

import com.news.domain.repository.CharactersRepository
import javax.inject.Inject

class GetFavoritesCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        charactersRepository.getFavoriteCharacters(ids)
}


