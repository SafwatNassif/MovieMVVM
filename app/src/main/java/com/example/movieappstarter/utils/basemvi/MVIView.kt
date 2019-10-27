package com.example.movieappstarter.utils.basemvi

import io.reactivex.Observable

interface MVIView<T : MVIIntent, S : MVIViewState> {

    fun intents(): Observable<T>

    fun render(state: S)
}