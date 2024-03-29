package com.example.movieappstarter.di.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.movieappstarter.application.MovieApp
import com.example.movieappstarter.di.component.AppComponent
import com.example.movieappstarter.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
object AppInjector {

    lateinit var appComponent: AppComponent

    fun init(movieApp: MovieApp) {
        appComponent = DaggerAppComponent.builder()
            .application(movieApp)
            .context(movieApp.baseContext)
            .build()

        appComponent.inject(movieApp)

        movieApp.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
                //Pass
            }

            override fun onActivityResumed(activity: Activity?) {
                //Pass
            }

            override fun onActivityStarted(activity: Activity?) {
                //Pass
            }

            override fun onActivityDestroyed(activity: Activity?) {
                //Pass
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                //Pass
            }

            override fun onActivityStopped(activity: Activity?) {
                //Pass
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                injectActivity(activity)
            }

        })
    }

    private fun injectActivity(activity: Activity?) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager
                        , fragment: Fragment
                        , savedInstanceState: Bundle?
                    ) {
                        if (fragment is Injectable) {
                            AndroidSupportInjection.inject(fragment)
                        }
                    }
                }, true
            )
        }

    }
}