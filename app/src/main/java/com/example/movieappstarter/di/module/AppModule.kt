package com.example.movieappstarter.di.module

import android.content.Context
import com.example.movieappstarter.data.local.SharedPrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefUtils(context: Context) = SharedPrefUtils(context)

}