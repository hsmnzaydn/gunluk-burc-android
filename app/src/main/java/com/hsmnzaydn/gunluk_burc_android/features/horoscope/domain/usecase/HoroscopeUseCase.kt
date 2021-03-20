package com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.usecase

import com.hsmnzaydn.base.core_network.CoreServiceCallback
import com.hsmnzaydn.gunluk_burc_android.base.BaseResponseCallback
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.data.entities.HoroscopeResponse
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.DescriptionListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.Horoscope
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.mapper.toDescriptionItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.repository.HoroscopeRepository
import toHoroscope
import toHoroscopeListItem
import javax.inject.Inject

class HoroscopeUseCase @Inject constructor(private val horoscopeRepository: HoroscopeRepository) {

    fun getHoroscopes(time: Long, callback: BaseResponseCallback<List<HoroscopeListItem>>) {
        horoscopeRepository.getHoroscopes(
            time,
            object : CoreServiceCallback<List<HoroscopeResponse>> {
                override fun onSuccess(response: List<HoroscopeResponse>?) {
                    response?.let {
                        callback.onSuccess(it.map {
                            it.toHoroscope().toHoroscopeListItem()
                        })
                    }
                }

                override fun onError(errorCode: Int, errorMessage: String) {
                    callback.onError(errorCode, errorMessage)
                }

            })
    }

    fun getDescriptionDetail(
        id: String,
        callback: BaseResponseCallback<Horoscope>,
        descriptionCallback: BaseResponseCallback<List<DescriptionListItem>>
    ) {
        horoscopeRepository.getHoroscopeDetail(
            id,
            object : CoreServiceCallback<HoroscopeResponse> {
                override fun onError(errorCode: Int, errorMessage: String) {
                    callback.onError(errorCode, errorMessage)
                }

                override fun onSuccess(response: HoroscopeResponse?) {
                    callback.onSuccess(response?.toHoroscope())
                    descriptionCallback.onSuccess(response?.toHoroscope()?.descriptions?.map {
                        it.toDescriptionItem()
                    })
                }
            })
    }
}