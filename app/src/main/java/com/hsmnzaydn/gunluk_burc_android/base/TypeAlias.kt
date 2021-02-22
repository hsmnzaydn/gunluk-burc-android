package com.hsmnzaydn.gunluk_burc_android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hsmnzaydn.gunluk_burc_android.utility.Event

typealias SingleLiveData<T> = LiveData<Event<T>>
typealias SingleMutableData<T> = MutableLiveData<Event<T>>

