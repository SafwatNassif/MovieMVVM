package com.example.movieappstarter.ui.home.fragment.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListDataSourceFactory
constructor(private val repository: MovieListRepository, private val compositeDisposable: CompositeDisposable) :
    DataSource.Factory<Int, Movie>() {

    private val movieListDataSource = MutableLiveData<MovieListDataSource>()
    lateinit var dataSource: MovieListDataSource

    override fun create(): DataSource<Int, Movie> {
        dataSource = MovieListDataSource(repository, compositeDisposable)
        movieListDataSource.postValue(dataSource)
        return dataSource
    }

    fun getMovieListDataSource() = movieListDataSource

}