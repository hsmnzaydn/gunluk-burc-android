package com.hsmnzaydn.gunluk_burc_android.base.model


sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: ErrorModel) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}