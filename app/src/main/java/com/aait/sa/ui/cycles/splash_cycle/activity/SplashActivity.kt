package com.aait.sa.ui.cycles.splash_cycle.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.aait.sa.R
import com.aait.sa.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}