package com.news.data.di

import com.news.data.api.ApiService
import com.news.data.repository.CharacterDetailRepositoryImpl
import com.news.data.repository.CharactersRepositoryImpl
import com.news.domain.repository.CharacterDetailRepository
import com.news.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(apiService: ApiService): CharactersRepository {
        return CharactersRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailRepository(apiService: ApiService): CharacterDetailRepository {
        return CharacterDetailRepositoryImpl(apiService)
    }
}