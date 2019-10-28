package com.example.movieappstarter.ui.home.fragment.list.mvi

import androidx.paging.PagedList
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.utils.basemvi.MVIResult

sealed class MovieListResult : MVIResult {

    sealed class LoadDataResult : MovieListResult() {
        data class Success(val data: PagedList<Movie>) : LoadDataResult()
    }
}