package com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities


import com.google.gson.annotations.SerializedName

data class HoroscopeResponse(
    @SerializedName("horoscopeName")
    var horoscopeName: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("imagePath")
    var imagePath: String
)