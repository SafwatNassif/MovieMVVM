package com.example.movieappstarter.ui.home.fragment.list.paging

import androidx.paging.PageKeyedDataSource
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Safwat Nassif on 7/30/2019.
 */
class MovieListDataSource
constructor(
    private val repository: MovieListRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {


    //    private val result = MutableLiveData<MovieListResult.LoadDataResult>()
//
//    private val _error = MutableLiveData<Throwable>()
//    private val _progressStatus = MutableLiveData<MovieListViewState>()
//
//
    private lateinit var params: LoadParams<Int>
    private lateinit var callback: LoadCallback<Int, Movie>
//
//    fun getErrorLiveStatus(): MutableLiveData<Throwable> {
//        return _error
//    }
//
//    fun getProgressStatus(): MutableLiveData<MovieListViewState> {
//        return _progressStatus
//    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val page = 1

        compositeDisposable.add(
            repository.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    callback.onResult(it.movies, null, page + 1)
                }.doOnError {

                }
                .subscribe()
        )


        /* repository.getPopularMovie(page)
             .subscribe(Consumer {
                 callback.onResult(it.movies, null, page + 1)
             }, Consumer {
                 _error.postValue(it)
             }, compositeDisposable, status = _progressStatus)*/

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.add(
            repository.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    callback.onResult(it.movies, params.key + 1)
                }.doOnError {
                    this.params = params
                    this.callback = callback
                }
                .subscribe()
        )


//        repository.getPopularMovie(params.key)
//            .subscribe(Consumer {
//                callback.onResult(it.movies, params.key + 1)
//            }, Consumer {
//                this.params = params
//                this.callback = callback
//                _error.postValue(it)
//            }, compositeDisposable, status = _progressStatus)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //pass
    }


    fun retry() {
        loadAfter(this.params, this.callback)
    }

}