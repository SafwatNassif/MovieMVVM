package com.example.movieappstarter.ui.home.fragment.list.mvi

import com.example.movieappstarter.utils.basemvi.MVIResult
import com.example.movieappstarter.data.model.PageMovie

sealed class MovieListResult : MVIResult {

    sealed class LoadDataResult : MovieListResult() {
        data class Success(val data: PageMovie) : LoadDataResult()
    }
}