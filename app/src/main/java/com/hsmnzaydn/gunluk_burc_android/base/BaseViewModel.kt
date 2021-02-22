package com.hsmnzaydn.gunluk_burc_android.base

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsmnzaydn.gunluk_burc_android.base.model.ApiErrorHandle
import com.hsmnzaydn.gunluk_burc_android.base.model.DataState
import com.hsmnzaydn.gunluk_burc_android.base.model.ErrorModel
import com.hsmnzaydn.gunluk_burc_android.utility.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseViewModel : ViewModel() {

    val errorHandle =
        MutableLiveData<Event<ErrorModel>>()
    val showLoading =
        MutableLiveData<Event<Boolean>>()
    val hideLoading =
        MutableLiveData<Event<Boolean>>()

    fun <T> execute(call: suspend () -> T): Flow<DataState<T>> = flow {
        emit(DataState.Loading)

        try {
            val result = call.invoke()
            emit(DataState.Success(result))

        } catch (e: Exception) {
            val error = ApiErrorHandle().traceErrorException(e)
            errorHandle.value = Event(error)
            emit(DataState.Error(error))
            hideLoading.value = Event(true)
        }
    }
}