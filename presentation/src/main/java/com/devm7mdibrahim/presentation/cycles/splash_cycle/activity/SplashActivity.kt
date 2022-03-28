package com.devm7mdibrahim.presentation.cycles.splash_cycle.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}