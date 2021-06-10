package com.example.latestearthquakes.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Buyukluk(
    @SerializedName("MD")
    val md: String?,
    @SerializedName("ML")
    val ml: String?,
    @SerializedName("Mw")
    val mw: String?
):Serializable
