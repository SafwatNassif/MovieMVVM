package com.example.movieappstarter.di.component

import android.content.Context
import com.example.movieappstarter.application.MovieApp
import com.example.movieappstarter.data.local.SharedPrefUtils
import com.example.movieappstarter.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

@Singleton
@Component(
    modules = [AndroidInjectionModule::class
        , AppModule::class
        , ViewModelModule::class
        , ActivityModuleBuilder::class
        , FragmentModuleBuilder::class
        , NetworkModule::class
        , RepositoryModule::class]
)
interface AppComponent {

    @Component.Builder
    interface AppBuilder {

        @BindsInstance
        fun application(movieApp: MovieApp): AppBuilder

        @BindsInstance
        fun context(context: Context): AppBuilder

        fun build(): AppComponent
    }


    fun inject(movieApp: MovieApp)
}