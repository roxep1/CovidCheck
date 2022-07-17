package com.bashkir.covidcheck.base

import androidx.navigation.NavController
import com.bashkir.covidcheck.ui.Screen
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun NavController.navigate(screen: Screen) = navigate(screen.destination)

fun LocalDateTime.formatCutToString(): String =
    if (this.toLocalDate() == LocalDate.now())
        "Сегодня в ${toLocalTime().formatToString()}" else toLocalDate().formatToString()

fun LocalDate.formatToString(): String =
    "${withZero(dayOfMonth)}.${withZero(monthValue)}.$year"

fun LocalTime.formatToString(): String =
    "${withZero(hour)}:${withZero(minute)}"

private fun withZero(num: Int): String =
    if (num.toString().length == 1) "0$num" else num.toString()

class LocalDateTimeJsonAdapter : TypeAdapter<LocalDateTime>() {
    override fun write(out: JsonWriter, value: LocalDateTime?) {
        out.value(value.toString())
    }

    override fun read(`in`: JsonReader): LocalDateTime =
        LocalDateTime.parse(`in`.nextString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
}