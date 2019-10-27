package com.example.movieappstarter.utils.basemvi

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface MviViewModel<T : MVIIntent, S : MVIViewState> {
    fun processIntents(intents: Observable<T>)

    fun state(): Observable<S>
}