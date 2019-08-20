package com.example.movieappstarter.ui.home.fragment.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieappstarter.data.local.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListDataSourceFactory
import com.example.movieappstarter.utils.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/26/2019.
 */

class MovieListFragmentViewModel @Inject
constructor(private val movieListRepository: MovieListRepository) : BaseViewModel() {

    fun fetchMovieList(): LiveData<PagedList<Movie>> {
        val factory = MovieListDataSourceFactory(movieListRepository, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()

        return LivePagedListBuilder<Int, Movie>(factory, config).build()
    }


}