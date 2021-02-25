package com.hsmnzaydn.gunluk_burc_android.utility

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager

import com.hsmnzaydn.gunluk_burc_android.R



fun Dialog.animationLoading(): Dialog {
    this.setCancelable(false)
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.setContentView(R.layout.loading_animation)
    this.window?.let {
        it.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // This flag is required to set otherwise the setDimAmount method will not show any effect
        it.setDimAmount(0.85f); //0 for no dim to 1 for full dim
    }
    return this
}


