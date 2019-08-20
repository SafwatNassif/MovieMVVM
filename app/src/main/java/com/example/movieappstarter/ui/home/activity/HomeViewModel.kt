package com.example.movieappstarter.ui.home.activity

import androidx.lifecycle.MutableLiveData
import com.example.movieappstarter.data.local.model.Movie
import com.example.movieappstarter.utils.base.BaseViewModel
import com.example.movieappstarter.utils.subscribe
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

}