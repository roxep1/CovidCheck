package com.bashkir.covidcheck.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.bashkir.covidcheck.base.formatCutToString
import com.bashkir.covidcheck.data.Async
import com.bashkir.covidcheck.data.CovidViewModel
import com.bashkir.covidcheck.data.models.DayStat
import com.bashkir.covidcheck.ui.components.StyledCard
import com.bashkir.covidcheck.ui.theme.headText
import com.bashkir.covidcheck.ui.theme.normalPadding
import com.bashkir.covidcheck.ui.theme.normalText

@Composable
fun CountryDetailScreen(navController: NavController, viewModel: CovidViewModel) =
    Box(Modifier.fillMaxSize()) {
        val stats by viewModel.uiState.detailStats.collectAsState(initial = Async.Uninitialized)

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Filled.ArrowBack, "Back button")
        }
        when (stats) {
            Async.Error -> Text(
                "Неудалось загрузить данные о стране :(",
                Modifier.align(Alignment.Center)
            )
            Async.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            is Async.Success -> (stats as Async.Success<List<DayStat>>).value.let { days ->
                if (days.isNotEmpty())
                    ScreenBody(stats = days)
                else Text(
                    "Нет сохраненых данных по этой стране :( \n" +
                            "Подключитесь к интернету и попробуйте еще раз",
                    Modifier.align(Alignment.Center).padding(normalPadding),
                    textAlign = TextAlign.Center
                )
            }
            Async.Uninitialized -> {}
        }
    }

@Composable
private fun ScreenBody(stats: List<DayStat>) = Column(Modifier.fillMaxSize()) {
    Text(
        stats.first().country,
        Modifier.fillMaxWidth(),
        fontSize = headText,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    LazyColumn(Modifier.fillMaxSize()) {
        items(stats.reversed()) {
            DayStatCard(stat = it)
        }
    }
}

@Composable
private fun DayStatCard(stat: DayStat) = StyledCard {
    Text(stat.date.formatCutToString(), fontSize = normalText, fontWeight = FontWeight.Bold)
    StatText(
        name = "Активные случаи",
        value = stat.active,
        if (isSystemInDarkTheme()) Color.White else Color.Black
    )
    StatText(name = "Случаи заболевания", value = stat.confirmed, Color.Yellow)
    StatText(name = "Летальные исходы", value = stat.deaths, Color.Red)
    StatText(name = "Случаи воздоровления", value = stat.recovered, Color.Green)
}

@Composable
private fun StatText(name: String, value: Int, valueColor: Color) =
    Text(buildAnnotatedString {
        append("$name:")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = valueColor)) {
            append(" $value")
        }
    })