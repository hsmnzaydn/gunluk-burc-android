package com.hsmnzaydn.gunluk_burc_android.base.model

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This class trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns [ErrorModel]
 *
 * */
class ApiErrorHandle {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {
            is HttpException -> {
                if (throwable.code() == 401) {
                    ErrorModel(
                        throwable.message(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNAUTHORIZED
                    )
                } else {
                    getHttpError(throwable)
                }
            }
            is SocketTimeoutException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.TIMEOUT)
            }
            is IOException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.NO_CONNECTION)
            }
            else -> null
        }
        return errorModel ?: ErrorModel(
            throwable?.message ?: "Bir sorun oluştu",
            0,
            ErrorModel.ErrorStatus.BAD_RESPONSE
        )
    }

    private fun getHttpError(throwable: HttpException?): ErrorModel? {
        return try {
            throwable?.let {
                it.response()?.errorBody()?.string()?.let { json ->
                    val jsonError = JSONObject(json)
                    val message = jsonError.get("message").toString()
                    ErrorModel(
                        message,
                        throwable.code(),
                        ErrorModel.ErrorStatus.BAD_RESPONSE,
                        throwable.message()
                    )
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                message = e.message,
                code = throwable?.code(),
                errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED
            )
        }

    }
}