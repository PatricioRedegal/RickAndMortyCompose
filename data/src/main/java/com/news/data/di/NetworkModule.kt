package com.news.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(@ApiEndpoint apiEndPoint: String, moshi: Moshi): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
        return Retrofit.Builder()
            .baseUrl(apiEndPoint)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClientBuilder.build())
            .build()
    }

    @Provides
    @ApiEndpoint
    fun provideApiEndpoint(): String = "https://rickandmortyapi.com/api/"
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiEndpoint
