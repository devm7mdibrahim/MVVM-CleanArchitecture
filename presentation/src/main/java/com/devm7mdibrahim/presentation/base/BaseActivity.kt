package com.devm7mdibrahim.presentation.base

import com.akexorcist.localizationactivity.ui.LocalizationActivity

abstract class BaseActivity : LocalizationActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}