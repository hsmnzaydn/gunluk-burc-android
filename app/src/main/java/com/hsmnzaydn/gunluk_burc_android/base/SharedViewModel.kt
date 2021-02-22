package com.hsmnzaydn.gunluk_burc_android.base


import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {
    val openCameraOption = SingleMutableData<Boolean>()
}