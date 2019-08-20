package com.example.movieappstarter.utils

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by Safwat Nassif on 7/30/2019.
 */

fun <T> Observable<T>.subscribe(
    success: Consumer<T>,
    error: Consumer<Throwable>,
    compositeDisposable: CompositeDisposable,
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeOnMainThread: Boolean = true
) {

    val observerScheduler =
        if (observeOnMainThread) AndroidSchedulers.mainThread()
        else subscribeScheduler

    compositeDisposable.add(
        this.subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .subscribe(success, error)
    )

}


fun <T> Observable<T>.subscribeWithAction(
    next: Consumer<T>,
    finish: Action,
    error: Consumer<Throwable>,
    compositeDisposable: CompositeDisposable,
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeOnMainThread: Boolean = true
) {

    val observerScheduler =
        if (observeOnMainThread) AndroidSchedulers.mainThread()
        else subscribeScheduler

    compositeDisposable.add(
        this.subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .subscribe(next, error, finish)
    )
}


fun <T> Single<T>.subscribe(
    success: Consumer<T>,
    error: Consumer<Throwable>,
    compositeDisposable: CompositeDisposable,
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeOnMainThread: Boolean = true,
    showError: Boolean = true
) {

    val observerScheduler =
        if (observeOnMainThread) AndroidSchedulers.mainThread()
        else subscribeScheduler


    compositeDisposable.add(
        this.subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .compose { single ->
                composeSingle<T>(single, showError)
            }
            .subscribe(success, error)
    )

}


private fun <T> composeSingle(single: Single<T>, showError: Boolean): Single<T> {
    return single
        .doOnError {

        }
        .doOnSubscribe {

        }

}
