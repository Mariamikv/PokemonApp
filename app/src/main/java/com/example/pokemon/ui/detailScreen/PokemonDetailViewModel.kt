package com.example.pokemon.ui.detailScreen

import androidx.lifecycle.ViewModel
import com.example.pokemon.data.responses.Pokemon
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    suspend fun getPokemon(name: String): Resource<Pokemon> {
        return repository.getPokemon(name)
    }
}