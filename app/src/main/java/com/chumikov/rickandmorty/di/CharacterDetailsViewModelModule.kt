package com.chumikov.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.chumikov.rickandmorty.presentation.CharacterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharacterDetailsViewModelModule {

    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    @Binds
    fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}