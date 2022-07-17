package com.bashkir.covidcheck.data.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("Countries") val countries: List<CountryStats>
)
