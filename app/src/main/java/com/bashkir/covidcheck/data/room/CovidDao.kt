package com.bashkir.covidcheck.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bashkir.covidcheck.data.models.CountryStats
import com.bashkir.covidcheck.data.models.DayStat

@Dao
interface CovidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountriesStats(countries: List<CountryStats>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountryDaysStat(stats: List<DayStat>)

    @Query("DELETE FROM day_stat WHERE country == :country")
    suspend fun deleteCountryDaysStat(country: String)

    @Query("SELECT * FROM countries")
    suspend fun getCountriesStats(): List<CountryStats>

    @Query("SELECT * FROM day_stat WHERE country == :country")
    suspend fun getCountryDaysStat(country: String): List<DayStat>
}