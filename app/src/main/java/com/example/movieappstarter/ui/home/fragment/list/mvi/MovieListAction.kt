package com.example.movieappstarter.ui.home.fragment.list.mvi

sealed class MovieListAction {
    object LoadData : MovieListAction()
    object NavigateToDetails : MovieListAction()

    data class LoadMore(val page: Int) : MovieListAction()


}