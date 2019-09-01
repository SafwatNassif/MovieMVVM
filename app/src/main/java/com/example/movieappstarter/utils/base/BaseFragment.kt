package com.example.movieappstarter.utils.base

import androidx.fragment.app.Fragment
import com.example.movieappstarter.di.helper.Injectable
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/26/2019.
 */
abstract class BaseFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory


}