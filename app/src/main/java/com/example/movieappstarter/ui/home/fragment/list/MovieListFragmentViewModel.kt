package com.example.movieappstarter.ui.home.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.blankj.utilcode.util.NetworkUtils
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.data.model.Status
import com.example.movieappstarter.data.remote.home.errorHandler.RetrofitException
import com.example.movieappstarter.ui.home.fragment.list.mvi.*
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListDataSourceFactory
import com.example.movieappstarter.utils.base.BaseViewModel
import com.example.movieappstarter.utils.basemvi.MviViewModel
import com.example.movieappstarter.utils.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/26/2019.
 */

class MovieListFragmentViewModel @Inject
constructor(private val movieListActionProcessor: MovieListActionProcessor) : BaseViewModel(),
    MviViewModel<MovieListIntent, MovieListViewState> {

    lateinit var movieList: LiveData<PagedList<Movie>>
    private val intentSubject: PublishSubject<MovieListIntent> = PublishSubject.create()
    private val movieListStateObserve: Observable<MovieListViewState> = compose()
    private val composDisposable = CompositeDisposable()

    private val intentFilter: ObservableTransformer<MovieListIntent, MovieListIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(MovieListIntent.InitialIntent::class.java).take(1),
                    shared.notOfType(MovieListIntent.InitialIntent::class.java)
                )
            }

        }

    lateinit var factory: MovieListDataSourceFactory


    override fun processIntents(intents: Observable<MovieListIntent>) {
        intents.subscribe(intentSubject)
    }

    override fun state(): Observable<MovieListViewState> {
        return movieListStateObserve
    }


    private fun compose(): Observable<MovieListViewState> {
        return intentSubject
            .compose(intentFilter)
            .map(this::fromIntentToAction)
            .compose(movieListActionProcessor.actionProcessor(compositeDisposable))
            .scan(MovieListViewState.idle(), reducer)
    }


    private fun fromIntentToAction(intent: MovieListIntent): MovieListAction {
        return when (intent) {
            is MovieListIntent.InitialIntent -> MovieListAction.LoadData
            is MovieListIntent.LoadMoreMovie -> MovieListAction.LoadMore(page = 2)
            is MovieListIntent.NavigateToDetails -> MovieListAction.NavigateToDetails
            is MovieListIntent.PullToRefresh -> MovieListAction.LoadData
        }
    }
/*
    fun fetchMovieList(): LiveData<PagedList<Movie>> {

        if (!::movieList.isInitialized) {
            factory = MovieListDataSourceFactory(movieListRepository, compositeDisposable)
            val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(true)
                .build()
            movieList = LivePagedListBuilder<Int, Movie>(factory, config).build()

            progressStatus = Transformations.switchMap(
                factory.getMovieListDataSource(),
                MovieListDataSource::getProgressStatus
            )

            Transformations.switchMap(
                factory.getMovieListDataSource(),
                MovieListDataSource::getErrorLiveStatus
            ).observeForever {
                handelError(it)
            }


        }

        return movieList
    }
*/

    fun refresh() {
        if (NetworkUtils.isConnected())
            factory.dataSource.invalidate()
        else
            handelError(RetrofitException.networkError(IOException("no Internet connection")))
    }


    fun retry() {
        factory.dataSource.retry()
    }


    companion object {
        val reducer = BiFunction { previousState: MovieListViewState, result: MovieListResult ->
            when (result) {
                is MovieListResult.LoadDataResult -> when (result) {
                    is MovieListResult.LoadDataResult.Success -> {
                        previousState.copy(
                            isLoadMore = false,
                            isRefresh = false,
                            isError = false,
                            isLoading = false,
                            movieList = result.data
                        )
                    }
                }
            }
        }
    }
}