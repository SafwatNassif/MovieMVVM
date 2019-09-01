package com.example.movieappstarter.ui.home.fragment.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.data.model.Status
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


    private val _error = MutableLiveData<Throwable>()
    private val _progressStatus = MutableLiveData<Status>()
    private lateinit var params: LoadParams<Int>
    private lateinit var callback: LoadCallback<Int, Movie>

    fun getErrorLiveStatus(): MutableLiveData<Throwable> {
        return _error
    }

    fun getProgressStatus(): MutableLiveData<Status> {
        return _progressStatus
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val page = 1
        repository.getPopularMovie(page)
                .subscribe(Consumer {
                    callback.onResult(it.movies, null, page + 1)
                }, Consumer {
                    _error.postValue(it)
                }, compositeDisposable, status = _progressStatus)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        repository.getPopularMovie(params.key)
                .subscribe(Consumer {
                    callback.onResult(it.movies, params.key + 1)
                }, Consumer {
                    this.params = params
                    this.callback = callback
                    _error.postValue(it)
                }, compositeDisposable, status = _progressStatus)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //pass
    }


    fun retry() {
        loadAfter(this.params, this.callback)
    }

}