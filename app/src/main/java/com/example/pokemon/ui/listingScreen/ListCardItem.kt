package com.example.pokemon.ui.listingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.pokemon.data.models.PokemonEntry
import com.example.pokemon.ui.theme.ButtonColor
import com.example.pokemon.ui.theme.CardTextColor
import java.util.*

@Composable
fun ListCardItem(
    entry: PokemonEntry,
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var pokemonColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    val showDialog =  remember { mutableStateOf(false) }

    var pokemonImage by remember {
        mutableStateOf("")
    }

    if(showDialog.value)
        AppDialog(
            pokemonImage = pokemonImage,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            dialogState = true
        )
    else
        showDialog.value = false

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = pokemonColor

    ) {
        Row {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onSuccess = {
                    viewModel.getPokemonColor(it.result.drawable) { color ->
                        pokemonColor = color
                    }
                },
                contentDescription = "pokemon image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(15.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clickable {
                        pokemonImage = entry.imageUrl
                        showDialog.value = true
                    }
            )

            Column {
                Text(
                    text = entry.pokemonName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    style = MaterialTheme.typography.body1,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 50.dp, top = 30.dp),
                    color = CardTextColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedButton(
                    modifier = Modifier
                        .padding(start = 100.dp, top = 10.dp)
                        .background(
                            color = ButtonColor,
                            shape = RoundedCornerShape(size = 12f)
                        ),
                    onClick = {
                        navController.navigate(
                            "pokemon_detail_screen/${pokemonColor.toArgb()}/${entry.pokemonName}"
                        )
                    },
                    text = "show Details"
                )
            }
        }
    }
}

@Composable
fun AppDialog(
    pokemonImage: String,
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    val dialogShape = RoundedCornerShape(16.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            buttons = {
                Column{
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(pokemonImage)
                            .crossfade(true)
                            .build(),
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.scale(0.5f)
                            )
                        },
                        contentDescription = "pokemon image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(15.dp)
                            .width(300.dp)
                            .height(300.dp)
                    )
                }
            },
            properties = DialogProperties(
                dismissOnClickOutside = true
            ),
            modifier = modifier,
            shape = dialogShape
        )
    }
}