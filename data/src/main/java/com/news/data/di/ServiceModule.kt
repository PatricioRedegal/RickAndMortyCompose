package com.news.data.di

import com.news.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideRickAndMortyApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}

