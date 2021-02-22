package com.basefy.base_mvp.core_local.core_local_di

import android.content.Context
import com.basefy.base_mvp.core_local.CoreLocalHelper
import com.basefy.base_mvp.core_local.CoreLocalHelperImp
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreLocalModules {

    /**
     * SharedPref'i provide etmek için kullanılır
     * @param context: Uygulamanın contextidir
     * @param gson: Strinleri classlara maplemek için kullanılır
     */
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context, gson: Gson): CoreLocalHelper =
        CoreLocalHelperImp(context, gson)


    @Provides
    @Singleton
    fun provideGson() = Gson()

}