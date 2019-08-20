package com.example.movieappstarter.di.module

import com.example.movieappstarter.ui.home.activity.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

@Module
abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector(modules = [FragmentModuleBuilder::class])
    abstract fun contributeMainActivity(): HomeActivity

}