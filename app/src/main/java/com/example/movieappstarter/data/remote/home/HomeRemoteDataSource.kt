package com.example.movieappstarter.data.remote.home

import com.example.movieappstarter.data.model.PageMovie
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
class HomeRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    private val popularMovieApi = retrofit.create(PopularMovieApi::class.java)

    fun getPopularMovie(page: Int): Single<PageMovie> {
        return popularMovieApi.getPopularMovies(page = page)
    }


}