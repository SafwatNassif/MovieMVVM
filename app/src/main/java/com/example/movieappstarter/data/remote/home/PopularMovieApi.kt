package com.example.movieappstarter.data.remote.home

import com.example.movieappstarter.BuildConfig
import com.example.movieappstarter.data.local.model.PageMovie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
interface PopularMovieApi {

    @GET("3/discover/movie")
    fun getPopularMovies(
        @Query("api_key") api_Key: String = BuildConfig.API_KEY,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int
    ): Single<PageMovie>

}