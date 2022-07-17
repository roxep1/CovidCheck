package com.bashkir.covidcheck.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bashkir.covidcheck.base.navigate
import com.bashkir.covidcheck.data.Async
import com.bashkir.covidcheck.data.CovidViewModel
import com.bashkir.covidcheck.data.models.CountryStats
import com.bashkir.covidcheck.ui.Screen
import com.bashkir.covidcheck.ui.components.SearchTextField
import com.bashkir.covidcheck.ui.components.StyledCard
import com.bashkir.covidcheck.ui.theme.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CountriesScreen(navController: NavController, viewModel: CovidViewModel) =
    Column(Modifier.fillMaxSize()) {
        val searchText = remember { mutableStateOf(TextFieldValue()) }
        val countries by viewModel.uiState.countries.collectAsState(Async.Uninitialized)

        SearchTextField(searchTextState = searchText)
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = countries is Async.Loading),
            onRefresh = viewModel::getCountries
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                when (countries) {
                    is Async.Success -> (countries as Async.Success<List<CountryStats>>).value.let { stats ->
                        if (stats.isNotEmpty())
                            items(stats.filter {
                                if (searchText.value.text.isNotBlank()) it.country.lowercase()
                                    .contains(searchText.value.text.trim())
                                else true
                            }) {
                                CountryCard(stats = it) {
                                    viewModel.getDaysStat(it)
                                    navController.navigate(Screen.CountryDetail)
                                }
                            }
                        else item {
                            Text(
                                "У вас пока нет сохраненных данных о странах \n" +
                                        "Подключитесь к интернету для получения данных",
                                Modifier
                                    .padding(normalPadding)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is Async.Loading -> items(10) { PlaceholderCard() }
                    is Async.Uninitialized -> {}
                    else -> item {
                        Text(
                            "Произошла ошибка при получении данных :(",
                            Modifier
                                .padding(normalPadding)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

@Composable
private fun CountryCard(stats: CountryStats, onClick: () -> Unit) =
    StyledCard(onClick = onClick) {
        Text(stats.country, fontSize = titleText, fontWeight = FontWeight.Bold)
        StatisticText(
            name = "Случаи заболевания",
            total = stats.totalConfirmed,
            new = stats.newConfirmed,
            lastNumColor = Color.Yellow
        )
        StatisticText(
            name = "Летальные исходы",
            total = stats.totalDeath,
            new = stats.newDeaths,
            lastNumColor = Color.Red
        )
        StatisticText(
            name = "Случаи выздоровления",
            total = stats.totalRecovered,
            new = stats.newRecovered,
            lastNumColor = Color.Green
        )
    }


@Composable
private fun PlaceholderCard() = Card(
    Modifier
        .fillMaxWidth()
        .padding(smallPadding)
        .placeholder(
            visible = true,
            color = Color.LightGray,
            highlight = PlaceholderHighlight.fade(Color.White),
            shape = RoundedCornerShape(10.dp)
        ),
    elevation = normalElevation
) {
    Spacer(Modifier.height(100.dp))
}

@Composable
private fun StatisticText(name: String, total: Int, new: Int, lastNumColor: Color) =
    Text(buildAnnotatedString {
        append("$name:")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append(" $total ")
        }
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = lastNumColor)) {
            append("(+${new})")
        }
    })