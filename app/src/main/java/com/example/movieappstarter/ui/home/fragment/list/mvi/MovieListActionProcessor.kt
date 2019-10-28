package com.example.movieappstarter.ui.home.fragment.list.mvi

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListDataSourceFactory
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieListActionProcessor @Inject
constructor(val movieListRepository: MovieListRepository) {


    private fun loadDataResult(compositeDisposable: CompositeDisposable) =
        ObservableTransformer<MovieListAction.LoadData, MovieListResult.LoadDataResult> { action ->
            action.flatMap {

                val factory = MovieListDataSourceFactory(movieListRepository, compositeDisposable)
                val config = PagedList.Config.Builder()
                    .setPageSize(20)
                    .setEnablePlaceholders(true)
                    .build()

                val movie = LivePagedListBuilder<Int, Movie>(factory, config)

                Observable.just(MovieListResult.LoadDataResult.Success(movie.build().value!!))
            }

        }


    fun actionProcessor(compositeDisposable: CompositeDisposable) =
        ObservableTransformer<MovieListAction, MovieListResult> { action ->
            action.ofType(MovieListAction.LoadData::class.java)
                .compose(loadDataResult(compositeDisposable))
        }

}