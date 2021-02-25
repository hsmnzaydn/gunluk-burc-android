package com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities


import com.google.gson.annotations.SerializedName

data class DescriptionResponse(
    @SerializedName("description")
    var description: String,
    @SerializedName("title")
    var title: String
)