package com.example.movieappstarter.di.module

import com.example.movieappstarter.ui.home.activity.HomeRepository
import com.example.movieappstarter.utils.base.BaseRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(homeRepository: HomeRepository): BaseRepository

}