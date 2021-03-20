package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.repository

import com.hsmnzaydn.base.core_network.CoreServiceCallback
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse

interface HoroscopeRepository{
    fun getHoroscopes(time:Long,callback: CoreServiceCallback<List<HoroscopeResponse>>)
    fun getHoroscopeDetail(id:String,callback: CoreServiceCallback<HoroscopeResponse>)
}