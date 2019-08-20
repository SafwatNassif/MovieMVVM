package com.example.movieappstarter.application

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.example.movieappstarter.di.helper.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
class MovieApp : Application(), HasActivityInjector, HasSupportFragmentInjector {


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector() = activityInjector
    override fun supportFragmentInjector() = fragmentInjector
    
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }


}