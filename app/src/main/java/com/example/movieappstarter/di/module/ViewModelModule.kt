package com.example.movieappstarter.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappstarter.di.helper.ViewModelKey
import com.example.movieappstarter.ui.home.activity.HomeViewModel
import com.example.movieappstarter.ui.home.fragment.list.MovieListFragmentViewModel
import com.example.movieappstarter.utils.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieListFragmentViewModel::class)
    abstract fun bindMovieListViewModel(movieListViewModel: MovieListFragmentViewModel): MovieListFragmentViewModel



}