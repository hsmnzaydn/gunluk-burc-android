package com.hsmnzaydn.gunluk_burc_android.ui.splash

import androidx.hilt.Assisted
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import com.hsmnzaydn.gunluk_burc_android.base.BaseViewModel
import com.hsmnzaydn.gunluk_burc_android.utility.Event
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(@Assisted private val stateHandle: SavedStateHandle): 
BaseViewModel(){


   fun showLoading(){
    showLoading.value = Event(true)
   }
 }