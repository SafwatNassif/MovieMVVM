package com.example.movieappstarter.ui.home.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.blankj.utilcode.util.NetworkUtils
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.data.model.Status
import com.example.movieappstarter.data.remote.home.errorHandler.RetrofitException
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListDataSource
import com.example.movieappstarter.ui.home.fragment.list.paging.MovieListDataSourceFactory
import com.example.movieappstarter.utils.base.BaseViewModel
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/26/2019.
 */

class MovieListFragmentViewModel @Inject
constructor(private val movieListRepository: MovieListRepository) : BaseViewModel() {

    lateinit var movieList: LiveData<PagedList<Movie>>
    lateinit var factory: MovieListDataSourceFactory

    private var progressStatus: LiveData<Status> = MutableLiveData<Status>()


    fun fetchMovieList(): LiveData<PagedList<Movie>> {

        if (!::movieList.isInitialized) {
            factory = MovieListDataSourceFactory(movieListRepository, compositeDisposable)
            val config = PagedList.Config.Builder()
                    .setPageSize(10)

                    .setEnablePlaceholders(false)
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


    fun refresh() {
        if (NetworkUtils.isConnected())
            factory.dataSource.invalidate()
        else
            handelError(RetrofitException.networkError(IOException("no Internet connection")))
    }

    fun getProgressStatus() = progressStatus

}