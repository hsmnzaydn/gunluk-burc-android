package com.hsmnzaydn.gunluk_burc_android.ui.horoscopedetail

import androidx.hilt.Assisted
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hsmnzaydn.gunluk_burc_android.base.BaseResponseCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import com.hsmnzaydn.gunluk_burc_android.base.BaseViewModel
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.DescriptionListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.Horoscope
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.usecase.HoroscopeUseCase
import com.hsmnzaydn.gunluk_burc_android.utility.Event
import javax.inject.Inject
@HiltViewModel
class HoroscopeDetailVM @Inject constructor(@Assisted private val stateHandle: SavedStateHandle,
private val horoscopeUseCase:HoroscopeUseCase):
BaseViewModel(){

    val horoscope = MutableLiveData<Horoscope>()
    val descriptionList = MutableLiveData<List<DescriptionListItem>>()
    fun getHoroscopeDetail(id: String) {
        showLoading.value = Event(true)
            horoscopeUseCase.getDescriptionDetail(id,
                object : BaseResponseCallback<Horoscope>(this) {
                    override fun onSuccess(response: Horoscope?) {
                        super.onSuccess(response)
                        horoscope.value = response
                    }
                },
                object : BaseResponseCallback<List<DescriptionListItem>>(this) {
                    override fun onSuccess(response: List<DescriptionListItem>?) {
                        super.onSuccess(response)
                        descriptionList.value = response
                        hideLoading.value = Event(true)

                    }
                })
    }

}