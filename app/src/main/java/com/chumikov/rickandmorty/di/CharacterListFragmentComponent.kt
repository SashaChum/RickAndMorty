package com.chumikov.rickandmorty.di

import com.chumikov.rickandmorty.presentation.CharacterDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CharacterDetailsViewModelModule::class])
interface CharacterListFragmentComponent {

    fun inject(fragment: CharacterDetailsFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(@BindsInstance id: Int): CharacterListFragmentComponent
    }
}