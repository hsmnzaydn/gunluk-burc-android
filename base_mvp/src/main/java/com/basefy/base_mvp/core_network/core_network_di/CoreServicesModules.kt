package com.basefy.base_mvp.core_network.core_network_di

import android.content.Context
import com.basefy.base_mvp.BuildConfig
import com.basefy.base_mvp.core_local.CoreLocalHelper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CoreServicesModules {

    /**
     * Dagger 2 için retrofiti provide eder
     * @param context: Uygulamanın contextini içerir
     */
    @Provides
    @Singleton
    fun provideRetrofitClient(@ApplicationContext context: Context, localHelper: CoreLocalHelper): Retrofit {
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(context.cacheDir, cacheSize.toLong())

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {

                    var request: Request =
                        chain.request().newBuilder()
                            .addHeader("Authorization", localHelper.getAuthorizationToken()?:"")
                            .addHeader("Accept-Encoding", "gzip, deflate")
                            .addHeader("User-Agent", "runscope/0.1")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Language", "TR")
                            .build()

                    return chain.proceed(request)
                }

            })
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }



}