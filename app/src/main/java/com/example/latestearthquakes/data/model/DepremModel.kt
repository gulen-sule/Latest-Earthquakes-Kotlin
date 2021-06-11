package com.example.latestearthquakes.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DepremModel(
    @SerializedName("dev")
    val dev: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("github_link")
    val github_link: String?,
    @SerializedName("depremler")
    var depremler: List<Depremler>? = null
) : Serializable
