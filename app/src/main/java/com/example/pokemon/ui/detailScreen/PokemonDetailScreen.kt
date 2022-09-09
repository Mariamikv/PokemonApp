package com.example.pokemon.ui.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.pokemon.data.responses.Pokemon
import com.example.pokemon.ui.listingScreen.BackButton
import com.example.pokemon.ui.theme.BackgroundColor
import com.example.pokemon.utils.Resource

@Composable
fun PokemonDetailScreen(
    pokemonColor: Color,
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        val pokemonInfo = produceState<Resource<Pokemon>>(
            initialValue = Resource.Loading()
        ){
            value = viewModel.getPokemon(pokemonName)
        }.value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ){

            BackButton(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(16.dp, 16.dp)
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )

            PokemonDetailStateWrapper(
                pokemonColor = pokemonColor,
                pokemonInfo = pokemonInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .clip(RoundedCornerShape(15.dp))
                    .background(pokemonColor)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                loadingModifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .padding(
                        top = 50.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (pokemonInfo is Resource.Success) {
                    pokemonInfo.data?.sprites?.let {
                        SubcomposeAsyncImage(
                            model = it.frontDefault,
                            contentDescription = pokemonInfo.data.name,
                            modifier = Modifier
                                .size(200.dp)
                                .offset(y = 80.dp)
                        )
                    }
                }
            }
        }
    }
}