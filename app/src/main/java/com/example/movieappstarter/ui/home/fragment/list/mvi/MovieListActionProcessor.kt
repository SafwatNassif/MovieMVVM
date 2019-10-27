package com.example.movieappstarter.ui.home.fragment.list.mvi

import com.example.movieappstarter.ui.home.fragment.list.MovieListRepository
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListActionProcessor @Inject
constructor(val movieListRepository: MovieListRepository) {


    private val loadDataResult =
        ObservableTransformer<MovieListAction.LoadData, MovieListResult.LoadDataResult> { action ->
            action.flatMap {
                movieListRepository.getPopularMovie(0)
                    .toObservable()
                    .map {
                        MovieListResult.LoadDataResult.Success(it)
                    }
                    .cast(MovieListResult.LoadDataResult::class.java)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }




    val actionProcessor = ObservableTransformer<MovieListAction, MovieListResult> { action ->
        action.ofType(MovieListAction.LoadData::class.java).compose(loadDataResult)

    }

}