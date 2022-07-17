package com.bashkir.covidcheck.data.room

import androidx.room.*
import com.bashkir.covidcheck.data.models.CountryStats
import com.bashkir.covidcheck.data.models.DayStat

@Database(
    entities = [CountryStats::class, DayStat::class],
    version = 2
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidDao

    companion object{
        const val NAME = "covid_db"
    }
}