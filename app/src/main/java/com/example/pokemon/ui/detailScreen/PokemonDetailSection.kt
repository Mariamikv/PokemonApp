package com.example.pokemon.ui.detailScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.data.responses.Pokemon
import com.example.pokemon.data.responses.Stat
import com.example.pokemon.data.responses.Type
import com.example.pokemon.ui.theme.PokemonNameColor
import com.example.pokemon.ui.theme.TypesBackgroundColor
import java.util.*
import kotlin.math.roundToInt

@Composable
fun PokemonDetailSection(
    pokemonColor: Color,
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 200.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "N${pokemonInfo.order} ${pokemonInfo.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }}",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            style = MaterialTheme.typography.body1,
            color = PokemonNameColor
        )

        TypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonForm = pokemonInfo.species.name,
            pokemonColor = pokemonColor,
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )
        PokemonStat(pokemonInfo.stats)
    }
}

@Composable
fun TypeSection(
    types: List<Type>
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(TypesBackgroundColor)
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonForm: String,
    pokemonColor: Color,
    pokemonWeight: Int,
    pokemonHeight: Int
) {
    val pokemonWeightInKg = remember {
        (pokemonWeight * 100f).roundToInt() / 1000f
    }
    val pokemonHeightInMeters = remember {
        (pokemonHeight * 100f).roundToInt() / 1000f
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Card(
            backgroundColor = pokemonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .clip(RectangleShape),
            border = BorderStroke(2.dp, Color.Black)

        ) {
            Text(
                text = "$pokemonForm Pokemon. Length: ${pokemonHeightInMeters}, Weight: $pokemonWeightInKg",
                color = Color.White,
                style = MaterialTheme.typography.body1,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(4.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun PokemonStat(
    stats: List<Stat>
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
    ) {
        for (stat in stats) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RectangleShape)
                    .background(TypesBackgroundColor)
                    .height(35.dp)
            ) {
                Text(
                    text = "${stat.stat.name}: ${stat.baseStat}",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}