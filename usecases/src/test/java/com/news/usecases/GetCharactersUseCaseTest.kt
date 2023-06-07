package com.news.usecases

import com.news.domain.model.ApiError
import com.news.domain.repository.CharactersRepository
import com.news.domain.util.StatusRequest
import com.news.usecases.characters.GetCharactersUseCase
import com.news.domain.model.Character
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    @RelaxedMockK
    private lateinit var charactersRepository: CharactersRepository

    lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getCharactersUseCase = GetCharactersUseCase(charactersRepository)
    }

    @Test
    fun `when searching by name and the api returns a list of characters`() = runBlocking {
        //Given
        val statusRequest =  StatusRequest(
            status = StatusRequest.Status.SUCCESS,
            data = listOf(
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    gender = "Male",
                    status = "Alive",
                    species = "Human",
                    origin = "Earth (C-137)"
                ),
                Character(
                    id = 8,
                    name = "Adjudicator Rick",
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
                    gender = "Male",
                    status = "Dead",
                    species = "Human",
                    origin = "Unknown"
                )
            )
        )
        coEvery { charactersRepository.getCharacters(name = "Rick") } returns statusRequest

        //When
        val response = getCharactersUseCase(name = "Rick")

        //Then
        coVerify(exactly = 1) { charactersRepository.getCharacters(name = any()) }
        assert(statusRequest == response)
    }

    @Test
    fun `when searching by name and the api returns 404 error`() = runBlocking {
        //Given
        val statusRequest =  StatusRequest(
            status = StatusRequest.Status.ERROR,
            data = listOf<Character>(),
            apiError = ApiError(code = 404)
        )
        coEvery { charactersRepository.getCharacters(name = "Ricky") } returns statusRequest

        //When
        val response = getCharactersUseCase(name = "Ricky")

        //Then
        coVerify(exactly = 1) { charactersRepository.getCharacters(name = any()) }
        assert(statusRequest == response)
    }
}