package com.chumikov.rickandmorty.di

import android.content.Context
import com.chumikov.rickandmorty.presentation.CharacterListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApiModule::class, DataModule::class, CharacterListViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: CharacterListFragment)

    fun characterListFragmentComponentFactory():CharacterListFragmentComponent.Factory

    fun episodeFragmentComponentFactory():EpisodeFragmentComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}