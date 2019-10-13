package com.raza.albumviewer.di

import com.raza.albumviewer.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeHomeActivity(): HomeActivity
}
