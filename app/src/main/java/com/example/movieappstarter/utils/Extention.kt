package com.example.movieappstarter.utils

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.NetworkUtils
import com.example.movieappstarter.data.model.Status
import com.example.movieappstarter.data.remote.home.errorHandler.RetrofitException
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.IOException

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
        status: MutableLiveData<Status>

) {

    val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler


    compositeDisposable.add(
            this.subscribeOn(subscribeScheduler)
                    .observeOn(observerScheduler)
                    .compose { single ->
                        composeSingle<T>(single, status)
                    }
                    .subscribe(success, error)
    )

}


private fun <T> composeSingle(
        single: Single<T>,
        status: MutableLiveData<Status>
): Single<T> {
    return single
            .doOnSubscribe {
                if (!NetworkUtils.isConnected()) {
                    throw RetrofitException.networkError(IOException("no Internet connection"))
                } else {
                    status.postValue(Status.LOADING)
                }
            }
            .doOnTerminate {
                status.postValue(Status.LOADED)
            }
}
