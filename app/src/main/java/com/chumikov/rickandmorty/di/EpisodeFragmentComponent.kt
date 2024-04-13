package com.chumikov.rickandmorty.di

import com.chumikov.rickandmorty.presentation.EpisodesFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [EpisodeViewModelModule::class])
interface EpisodeFragmentComponent {

    fun inject(fragment: EpisodesFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance ids: List<Int>): EpisodeFragmentComponent
    }
}