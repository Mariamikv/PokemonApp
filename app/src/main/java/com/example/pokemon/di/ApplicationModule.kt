package com.example.pokemon.di

import com.example.pokemon.data.service.ApiService
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun providePokemonRepository(service: ApiService) = PokemonRepository(service)

    @Singleton
    @Provides
    fun providesApiService(): ApiService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }
}