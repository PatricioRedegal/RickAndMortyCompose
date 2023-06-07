package com.news.data.api

import com.news.data.api.ApiEndPoints.CHARACTERS
import com.news.data.models.characters.CharacterDto
import com.news.data.models.characters.CharactersDto
import com.news.data.models.characters.singleCharacter.CharacterDetailDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET(CHARACTERS)
    suspend fun getCharacters(
        @Query("page") page: Int? = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null
    ): Response<CharactersDto>

    @GET("$CHARACTERS/{ids}")
    suspend fun getCharactersById(
        @Path("ids") ids: String
    ): Response<List<CharacterDto>>

    @GET("$CHARACTERS/{character_id}")
    suspend fun getCharacterDetails(
        @Path("character_id") characterId: Int
    ): Response<CharacterDetailDto>
}
