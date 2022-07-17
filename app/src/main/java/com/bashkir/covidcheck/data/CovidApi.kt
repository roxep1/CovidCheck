package com.bashkir.covidcheck.data

import com.bashkir.covidcheck.data.models.DayStat
import com.bashkir.covidcheck.data.models.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDateTime

interface CovidApi {
    companion object {
        const val BASE_URL = "https://api.covid19api.com/"
    }

    @GET("summary")
    suspend fun getCountries(): Response

    @GET("live/country/{country}/status/confirmed/date/{date}")
    suspend fun getCountryStats(
        @Path("country") slug: String,
        @Path("date") afterDate: LocalDateTime = LocalDateTime.now().minusWeeks(2)
    ): List<DayStat>
}