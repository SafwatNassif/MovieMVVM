package com.example.movieappstarter.data.model

import androidx.annotation.StringRes


sealed class Status {

    object LOADING : Status()
    object LOADED : Status()
    object EMPTY_LIST : Status()
    object NO_INTERNET : Status()
    object UN_EXPECTED : Status()
    object UNKNOWN : Status()

    open class Error(var remoteError: String? = null, @StringRes var localError: Int? = null)


}