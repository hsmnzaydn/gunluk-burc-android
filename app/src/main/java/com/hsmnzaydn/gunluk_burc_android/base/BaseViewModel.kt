package com.hsmnzaydn.gunluk_burc_android.base

import android.os.Handler
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    fun delay(duration: Long, run: () -> Unit) {
        Handler().postDelayed({
            run()
        }, duration)
    }
}