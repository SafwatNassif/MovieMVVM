package com.example.movieappstarter.ui.home.fragment.list.paging

import androidx.paging.DataSource
import com.example.movieappstarter.data.local.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListDataSourceFactory
constructor(private val repository: MovieListRepository, private val compositeDisposable: CompositeDisposable) :
    DataSource.Factory<Int, Movie>() {


    override fun create(): DataSource<Int, Movie> {
        return MovieListDataSource(repository, compositeDisposable)
    }

}