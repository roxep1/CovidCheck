package com.bashkir.covidcheck.data

import android.content.Context
import androidx.room.Room
import com.bashkir.covidcheck.data.room.AppDatabase
import com.bashkir.covidcheck.data.room.CovidDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Singleton
    @Provides
    fun provideApi(): CovidApi = Retrofit.Builder()
        .baseUrl(CovidApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CovidApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, AppDatabase.NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCovidDao(database: AppDatabase): CovidDao = database.covidDao()
}