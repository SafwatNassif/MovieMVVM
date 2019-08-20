package com.example.movieappstarter.utils.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/24/2019.
 */
abstract class BaseActivity<mViewModel : BaseViewModel> : FragmentActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModel: mViewModel

    override fun supportFragmentInjector() = dispatchingAndroidInjector


}