package com.bashkir.covidcheck.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bashkir.covidcheck.data.models.CountryStats
import com.bashkir.covidcheck.data.models.DayStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CovidViewModel @Inject constructor(private val service: CovidService) : ViewModel() {
    var uiState by mutableStateOf(UiState())
        private set

    init {
        getCountries()
    }

    fun getCountries() {
        uiState = uiState.copy(countries = load(service::getCountries))
    }

    fun getDaysStat(countryStats: CountryStats) {
        uiState = uiState.copy(detailStats = load { service.getCountryStats(countryStats) })
    }

    private fun <T> load(getValue: suspend () -> T): Flow<Async<T>> = flow {
        emit(Async.Loading)
        emit(Async.Success(getValue()))
    }.catch { emit(Async.Error) }
}

sealed class Async<out T> {
    object Uninitialized : Async<Nothing>()
    object Loading : Async<Nothing>()
    data class Success<T>(val value: T) : Async<T>()
    object Error : Async<Nothing>()
}

data class UiState(
    val countries: Flow<Async<List<CountryStats>>> = flow { emit(Async.Uninitialized) },
    val detailStats: Flow<Async<List<DayStat>>> = flow { emit(Async.Uninitialized) }
)