package com.raza.albumviewer.di

import com.raza.albumviewer.ui.album.albums.AlbumsFragment
import com.raza.albumviewer.ui.album.albumdetail.DetailFragment
import com.raza.albumviewer.ui.home.HomeFragment
import com.raza.albumviewer.ui.search.SearchResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchResultsFragment(): SearchResultsFragment

    @ContributesAndroidInjector
    abstract fun contributeAlbumsFragment(): AlbumsFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}