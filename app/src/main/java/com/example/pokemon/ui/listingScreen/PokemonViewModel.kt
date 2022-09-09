package com.example.pokemon.ui.listingScreen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokemon.data.models.PokemonEntry
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.utils.Constants.PAGE_SIZE
import com.example.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {

    private var page = 0
    var pokemonList = mutableStateOf<List<PokemonEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPagination()
    }

    fun loadPagination() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, page * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    endReached.value = page * PAGE_SIZE >= result.data!!.count
                    val entries = result.data.results.mapIndexed { _, value ->
                        val imageId = if(value.url.endsWith("/")) {
                            value.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            value.url.takeLastWhile { it.isDigit() }
                        }

                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${imageId}.png"
                        PokemonEntry(value.name, imageUrl, imageId.toInt())
                    }
                    page++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += entries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                else -> {}
            }
        }
    }

    fun getPokemonColor(drawable: Drawable, getColor: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let {
                getColor(Color(it))
            }
        }
    }
}