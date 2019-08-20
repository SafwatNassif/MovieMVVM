package com.example.movieappstarter.ui.home.fragment.list.paging

import androidx.paging.PageKeyedDataSource
import com.example.movieappstarter.data.local.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import com.example.movieappstarter.utils.subscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListDataSource
constructor(
    private val repository: MovieListRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val page = 1
        repository.getPopularMovie(page)
            .subscribe(
                Consumer {
                    callback.onResult(it.movies, null, page + 1)
                }, Consumer {

                }, compositeDisposable
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        repository.getPopularMovie(params.key)
            .subscribe(Consumer {
                callback.onResult(it.movies,params.key+1)
            }, Consumer {

            }, compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //pass
    }
}