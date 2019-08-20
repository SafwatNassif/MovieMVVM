package com.example.movieappstarter.utils.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }
}