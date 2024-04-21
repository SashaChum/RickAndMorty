package com.chumikov.rickandmorty.di

import android.content.Context
import com.chumikov.rickandmorty.presentation.CharacterDetailsFragment
import com.chumikov.rickandmorty.presentation.CharacterListFragment
import com.chumikov.rickandmorty.presentation.EpisodesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApiModule::class, DataModule::class, CharacterListViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: CharacterListFragment)
    fun inject(fragment: CharacterDetailsFragment)
    fun inject(fragment: EpisodesFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}