package com.example.movieappstarter.ui.home.fragment.list.mvi

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieappstarter.data.model.Movie
import com.example.movieappstarter.utils.basemvi.MVIViewState

data class MovieListViewState(
    val isLoading: Boolean,
    val isError: Boolean,
    val isRefresh: Boolean,
    val isLoadMore: Boolean,
    val movieList: PagedList<Movie>?
) : MVIViewState {

    companion object {
        fun idle(): MovieListViewState {
            return MovieListViewState(
                isLoading = true,
                isError = false,
                isRefresh = false,
                isLoadMore = false,
                movieList = null
            )
        }
    }
}