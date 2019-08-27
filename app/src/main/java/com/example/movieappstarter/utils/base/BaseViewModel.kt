package com.example.movieappstarter.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappstarter.R
import com.example.movieappstarter.data.model.Status
import com.example.movieappstarter.data.remote.home.errorHandler.RetrofitException
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val error = MutableLiveData<Status.Error>()
    val loadMore = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }

    fun handelError(exception: Throwable?) {
        if (exception is RetrofitException) {
            when (exception.getKind()) {
                RetrofitException.Kind.NETWORK -> {
                    error.value = Status.Error(localError = R.string.no_internet)
                }
                RetrofitException.Kind.HTTP -> {
                    error.value = Status.Error(exception.message ?: "")
                }
                RetrofitException.Kind.UNEXPECTED -> {
                    error.value = Status.Error(exception.message ?: "")
                }
            }
        }
    }
}