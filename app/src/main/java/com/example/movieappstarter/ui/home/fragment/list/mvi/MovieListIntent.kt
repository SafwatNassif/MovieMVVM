package com.example.movieappstarter.ui.home.fragment.list.mvi

import com.example.movieappstarter.utils.basemvi.MVIIntent

sealed class MovieListIntent : MVIIntent {

    object InitialIntent : MovieListIntent()
    object LoadMoreMovie : MovieListIntent()
    object PullToRefresh : MovieListIntent()
    object NavigateToDetails : MovieListIntent()
}