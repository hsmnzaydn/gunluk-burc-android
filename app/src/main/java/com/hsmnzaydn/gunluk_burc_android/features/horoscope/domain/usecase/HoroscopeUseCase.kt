package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.usecase

import com.basefy.base_mvp.core_network.CoreServiceCallback
import com.hsmnzaydn.gunluk_burc_android.base.BaseResponseCallback
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.repository.HoroscopeRepository
import toHoroscopeListItem
import javax.inject.Inject

class HoroscopeUseCase @Inject constructor(private val horoscopeRepository: HoroscopeRepository){

    fun getHoroscopes(time:Long,callback: BaseResponseCallback<List<HoroscopeListItem>>){
        horoscopeRepository.getHoroscopes(time, object: CoreServiceCallback<List<HoroscopeResponse>> {
            override fun onSuccess(response: List<HoroscopeResponse>?) {
                response?.let {
                    callback.onSuccess(it.map {
                      it.toHoroscopeListItem()
                    })
                }
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                callback.onError(errorCode, errorMessage)
            }

        })
    }
}