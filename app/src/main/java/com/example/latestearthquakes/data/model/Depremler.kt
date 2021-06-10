package com.example.latestearthquakes.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Depremler(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Tarih")
    val tarih: String?,
    @SerializedName("Saat")
    val saat: String?,
    @SerializedName("Unix_time")
    val unix_time: Double,
    @SerializedName("Enlem(N)")
    val enlem: Double,
    @SerializedName("Boylam(E)")
    val boylam: Double,
    @SerializedName("Derinlik(km)")
    val derinlik: Double,
    @SerializedName("Buyukluk")
    val buyukluk: Buyukluk,
    @SerializedName("Yer")
    val yer: String?,
    @SerializedName("Nitelik")
    val nitelik: String?

) : Serializable
