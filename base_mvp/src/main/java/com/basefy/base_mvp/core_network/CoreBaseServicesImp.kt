package com.basefy.base_mvp.core_network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

open class CoreBaseServicesImp @Inject constructor(
    private val retrofit: Retrofit
) {
    /**
     * Request atmak için kullanılır
     * @param callbackCore: Veri gelince verinin ön tarafa aktarılacağı callback classıdır
     * @param observable: Verinin sunucudan geldiği zaman dinleneceği şekli belirler
     */
    fun <T : CoreBaseResponse<*>> getRequest(
        callbackCore: CoreServiceCallback<T>,
        observable: () -> Observable<T>
    ): Disposable = observable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(onNext(callbackCore)) {
            callbackCore.onError(
                CoreNetworkError(it).errorCode, CoreNetworkError(
                    it
                ).appErrorMessage
            )
        }

    fun <T : CoreBaseResponse<*>> onNext(callbackCore: CoreServiceCallback<T>): (T) -> Unit = {
        when {
            it.code == 200 -> callbackCore.onSuccess(it)
            it.code == 401 -> callbackCore.onError(it.code, it.error!!)
            it.code == 500 -> callbackCore.onError(it.code, it.error!!)

            else -> callbackCore.onError(it.code, it.error!!)
        }
    }

    /**
     * Resimler için request atmak için kullanılır
     * @param callbackCore: Veri gelince verinin ön tarafa aktarılacağı callback classıdır
     * @param observable: Verinin sunucudan geldiği zaman dinleneceği şekli belirler
     */
    fun getUploadRequest(
        callbackCore: CoreServiceCallback<UploadImageResponseModel>,
        observable: () -> Observable<UploadImageResponseModel>
    ): Disposable = observable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(callbackCore::onSuccess) {
            callbackCore.onError(
                CoreNetworkError(it).errorCode, CoreNetworkError(
                    it
                ).appErrorMessage
            )
        }

}