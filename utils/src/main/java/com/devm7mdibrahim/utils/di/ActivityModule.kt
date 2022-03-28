package com.devm7mdibrahim.utils.di

import android.app.Activity
import com.devm7mdibrahim.utils.common.ProgressUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun provideProgressUtil(activity: Activity): ProgressUtil {
        return ProgressUtil(activity)
    }

}