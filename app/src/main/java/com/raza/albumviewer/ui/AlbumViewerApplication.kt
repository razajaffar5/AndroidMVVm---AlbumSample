package com.raza.albumviewer.ui

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.raza.albumviewer.BuildConfig
import com.raza.albumviewer.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class AlbumViewerApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

/*
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())
*/

        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}