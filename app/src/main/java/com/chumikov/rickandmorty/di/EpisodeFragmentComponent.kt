package com.chumikov.rickandmorty.di

import com.chumikov.rickandmorty.presentation.CharacterDetailsFragment
import com.chumikov.rickandmorty.presentation.EpisodeFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [EpisodeViewModelModule::class])
interface EpisodeFragmentComponent {

    fun inject(fragment: EpisodeFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance ids: List<Int>): EpisodeFragmentComponent
    }
}