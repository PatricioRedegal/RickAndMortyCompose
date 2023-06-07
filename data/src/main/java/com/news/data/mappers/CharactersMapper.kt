package com.news.data.mappers

import com.news.data.models.characters.CharacterDto
import com.news.data.models.characters.CharactersDto
import com.news.data.models.characters.singleCharacter.CharacterDetailDto
import com.news.domain.model.Character
import com.news.domain.model.CharacterDetail

fun CharactersDto.toCharactersList(): List<Character> =
    results.map { character ->
        Character(
            id = character.id,
            name = character.name,
            imageUrl = character.image,
            gender = character.gender,
            status = character.status,
            origin = character.origin.name,
            species = character.species
        )
    }


fun CharacterDto.toCharactersList(): Character = Character(
    id = id,
    name = name,
    imageUrl = image,
    gender = gender,
    status = status,
    origin = origin.name,
    species = species
)

fun CharacterDetailDto.toCharacterDetail(): CharacterDetail = CharacterDetail(
    id = id,
    episode = episode,
    name = name,
    image = image,
    gender = gender,
    status = status,
    location = location.name,
    origin = origin.name,
    species = species,
    type = type
)
