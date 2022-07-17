package com.bashkir.covidcheck.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.bashkir.covidcheck.data.models.CountryStats
import com.bashkir.covidcheck.data.models.DayStat
import com.bashkir.covidcheck.data.room.AppDatabase
import com.bashkir.covidcheck.data.room.CovidDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CovidService @Inject constructor(
    private val api: CovidApi,
    private val covidDao: CovidDao,
    @ApplicationContext private val context: Context
) {

    suspend fun getCountries(): List<CountryStats> =
        if (isOnline()) {
            val countries = api.getCountries().countries
            covidDao.insertCountriesStats(countries)
            countries
        } else covidDao.getCountriesStats()


    suspend fun getCountryStats(country: CountryStats): List<DayStat> = if (isOnline()) {
        val stats = api.getCountryStats(country.slug)
        covidDao.deleteCountryDaysStat(country.country)
        covidDao.insertCountryDaysStat(stats)
        stats
    } else covidDao.getCountryDaysStat(country.country)


    private fun isOnline(): Boolean =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            } ?: false
        }
}