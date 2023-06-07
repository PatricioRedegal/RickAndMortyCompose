package com.news.usecases.characterDetail

import com.news.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository
) {
    suspend operator fun invoke(id: Int) =
        characterDetailRepository.getCharacterDetail(id)
}


