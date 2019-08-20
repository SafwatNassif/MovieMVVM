package com.example.movieappstarter.ui.home.activity

import android.os.Bundle
import com.example.movieappstarter.R
import com.example.movieappstarter.utils.base.BaseActivity

class HomeActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
