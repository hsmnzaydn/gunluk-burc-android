package com.hsmnzaydn.gunluk_burc_android.base

import com.basefy.base_mvp.core_network.CoreServiceCallback


abstract class BaseResponseCallback<R> constructor(
    private val viewModel: BaseViewModel?
) : CoreServiceCallback<R> {

    /**
     * Servisten gelen veri ön tarafa aktarılırken kullanılır
     * @param response: Dönen cevabın verildiği parametredir
     */
    override fun onSuccess(response: R?) {

    }

    /**
     * Servisten dönen herhangi bir hatayı ön tarafa aktarırken kullanılır
     * @param errorCode: Dönen hata kodudur
     * @param errorMessage: Dönen hata mesajıdır
     */
    override fun onError(errorCode: Int, errorMessage: String) {

    }
}