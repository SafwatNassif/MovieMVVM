package com.example.movieappstarter.di.module

import com.example.movieappstarter.ui.home.fragment.list.MovieListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Safwat Nassif on 7/26/2019.
 */
@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector
    abstract fun bindMovieListFragment(): MovieListFragment
}