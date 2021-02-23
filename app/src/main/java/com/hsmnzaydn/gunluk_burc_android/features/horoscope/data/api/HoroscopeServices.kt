package com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.api

import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface HoroscopeServices {
    @GET("horoscopes")
    fun getHoroscopes(@Query("date") date:Long): Single<List<HoroscopeResponse>>
}