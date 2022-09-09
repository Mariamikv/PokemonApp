package com.example.pokemon.repository

import com.example.pokemon.data.responses.Pokemon
import com.example.pokemon.data.responses.PokemonList
import com.example.pokemon.data.service.ApiService
import com.example.pokemon.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(private val service: ApiService) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            service.getPokemonList(limit, offset)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }

        return Resource.Success(response)
    }

    suspend fun getPokemon(name: String): Resource<Pokemon> {
        val response = try {
            service.getPokemon(name)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }

        return Resource.Success(response)
    }
}