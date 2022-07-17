package com.bashkir.covidcheck.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashkir.covidcheck.base.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

@Entity(tableName = "day_stat", primaryKeys = ["date", "country"])
data class DayStat(
    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    @SerializedName("Date")
    val date: LocalDateTime,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Confirmed")
    val confirmed: Int,
    @SerializedName("Deaths")
    val deaths: Int,
    @SerializedName("Recovered")
    val recovered: Int,
    @SerializedName("Active")
    val active: Int
)
