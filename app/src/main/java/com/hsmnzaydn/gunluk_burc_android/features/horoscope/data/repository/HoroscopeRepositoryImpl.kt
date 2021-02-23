package com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.repository

import com.basefy.base_mvp.core_network.CoreBaseServicesImp
import com.basefy.base_mvp.core_network.CoreServiceCallback
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.api.HoroscopeServices
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import retrofit2.Retrofit
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.repository.HoroscopeRepository

class HoroscopeRepositoryImpl(private val retrofit: Retrofit) : CoreBaseServicesImp(retrofit),
    HoroscopeRepository {
    fun getHoroscopeServices(): HoroscopeServices = retrofit.create(HoroscopeServices::class.java)
    override fun getHoroscopes(time: Long, callback: CoreServiceCallback<List<HoroscopeResponse>>) {
        getRequest(callback){
            getHoroscopeServices().getHoroscopes(time)
        }
    }


}