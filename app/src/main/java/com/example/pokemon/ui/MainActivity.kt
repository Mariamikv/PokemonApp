package com.example.pokemon.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.detailScreen.PokemonDetailScreen
import com.example.pokemon.ui.listingScreen.ListingScreen
import com.example.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "listing_screen"
                ) {
                    composable("listing_screen") {
                        ListingScreen(navController)
                    }
                    composable(
                        "pokemon_detail_screen/{pokemonColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val pokemonColor = remember {
                            val color = it.arguments?.getInt("pokemonColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }

                        if (pokemonName != null) {
                            PokemonDetailScreen(
                                pokemonColor = pokemonColor,
                                pokemonName = pokemonName.lowercase(Locale.ROOT),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
