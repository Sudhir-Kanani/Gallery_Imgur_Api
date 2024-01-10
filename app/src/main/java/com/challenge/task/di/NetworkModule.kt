package com.challenge.task.di

import com.challenge.task.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module responsible for providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    /**
     * Provides a singleton instance of Retrofit configured with the base URL and Gson converter factory.
     * @return The Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.imgur.com/3/").addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    /**
     * Provides a singleton instance of the ImageApi interface using the provided Retrofit instance.
     * @param provideRetrofit The Retrofit instance.
     * @return The ImageApi instance.
     */
    @Singleton
    @Provides
    fun provideApi(provideRetrofit: Retrofit): ImageApi {
        return provideRetrofit.create(ImageApi::class.java)
    }
}