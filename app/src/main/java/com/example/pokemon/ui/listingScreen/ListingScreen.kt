package com.example.pokemon.ui.listingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.R
import com.example.pokemon.ui.theme.BackgroundColor

@Composable
fun ListingScreen(
    navController: NavController
) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_pokemon_logo_svg),
                contentDescription = stringResource(id = R.string.Pokemon_icon),
                modifier = Modifier
                    .align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn(
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val itemCount = pokemonList.size
        items(itemCount) {
            if (it >= itemCount - 1 && !isLoading) {
                viewModel.loadPagination()
            }

            ListCardItem(
                entry = pokemonList[it],
                navController = navController
            )
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPagination()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(
            error,
            color = Color.Blue,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}