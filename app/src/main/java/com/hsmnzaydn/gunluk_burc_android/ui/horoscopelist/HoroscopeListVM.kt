package com.hsmnzaydn.gunluk_burc_android.ui.horoscopelist

import androidx.hilt.Assisted
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.hsmnzaydn.gunluk_burc_android.base.BaseResponseCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import com.hsmnzaydn.gunluk_burc_android.base.BaseViewModel
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.entities.HoroscopeListItem
import com.hsmnzaydn.gunluk_burc_android.features.horoscope.domain.usecase.HoroscopeUseCase
import com.hsmnzaydn.gunluk_burc_android.utility.Event
import com.hsmnzaydn.gunluk_burc_android.base.model.BaseEntity
import javax.inject.Inject

@HiltViewModel
class HoroscopeListVM @Inject constructor(@Assisted private val stateHandle: SavedStateHandle,
private val horoscopeUseCase: HoroscopeUseCase
) :
    BaseViewModel() {

    val horoscopeList = MutableLiveData<List<BaseEntity>>()

    fun getHoroscopes(time:Long) {
        showLoading.value = Event(true)
        horoscopeUseCase.getHoroscopes(time,object:BaseResponseCallback<List<HoroscopeListItem>>(this){
            override fun onSuccess(response: List<HoroscopeListItem>?) {
                super.onSuccess(response)
                horoscopeList.value = response
                hideLoading.value = Event(true)
            }
        })
    }

}