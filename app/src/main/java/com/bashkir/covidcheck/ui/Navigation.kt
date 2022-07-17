package com.bashkir.covidcheck.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashkir.covidcheck.data.CovidViewModel
import com.bashkir.covidcheck.ui.screens.CountriesScreen
import com.bashkir.covidcheck.ui.screens.CountryDetailScreen

enum class Screen(val destination: String) {
    Countries("countries"), CountryDetail("country_detail");

    companion object {
        const val MAIN_ROUTE = "main"
    }
}

@Composable
fun MainNavHost(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Countries.destination,
    route = Screen.MAIN_ROUTE
) {
    composable(Screen.Countries.destination) {
        CountriesScreen(
            navController = navHostController,
            viewModel = navHostController.getViewModel(it)
        )
    }

    composable(Screen.CountryDetail.destination) {
        CountryDetailScreen(
            navController = navHostController,
            viewModel = navHostController.getViewModel(it)
        )
    }
}

@Composable
private fun NavController.getViewModel(backStackEntry: NavBackStackEntry): CovidViewModel =
    hiltViewModel(remember(backStackEntry) { getBackStackEntry(Screen.MAIN_ROUTE) })