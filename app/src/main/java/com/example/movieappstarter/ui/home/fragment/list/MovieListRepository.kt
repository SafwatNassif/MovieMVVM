package com.example.movieappstarter.ui.home.fragment.list

import com.example.movieappstarter.data.local.model.PageMovie
import com.example.movieappstarter.data.remote.home.HomeRemoteDataSource
import com.example.movieappstarter.utils.base.BaseRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/26/2019.
 */
class MovieListRepository @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var homeRemoteDataSource: HomeRemoteDataSource

    fun getPopularMovie(page: Int): Single<PageMovie> {
        return homeRemoteDataSource.getPopularMovie(page)
    }

}