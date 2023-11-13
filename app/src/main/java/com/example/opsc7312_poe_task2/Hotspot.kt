package com.example.opsc7312_poe_task2

import com.google.gson.annotations.SerializedName

data class Hotspot(
    @SerializedName("countryCode") val countryCode: String? = null,
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("latestObsDt") val latestObsDt: String? = null,
    @SerializedName("lng") val lng: Double? = null,
    @SerializedName("locId") val locId: String? = null,
    @SerializedName("locName") val locName: String? = null,
    @SerializedName("numSpeciesAllTime") val numSpeciesAllTime: Int? = null,
    @SerializedName("subnational1Code") val subnational1Code: String? = null
)
