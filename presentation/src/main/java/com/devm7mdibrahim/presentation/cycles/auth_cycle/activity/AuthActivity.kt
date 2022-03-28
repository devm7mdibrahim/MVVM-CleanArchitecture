package com.devm7mdibrahim.presentation.cycles.auth_cycle.activity

import android.os.Bundle
import com.devm7mdibrahim.presentation.R
import com.devm7mdibrahim.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}