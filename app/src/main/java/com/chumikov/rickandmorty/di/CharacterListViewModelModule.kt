package com.chumikov.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.chumikov.rickandmorty.presentation.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharacterListViewModelModule {

    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    @Binds
    fun bindCharacterListViewModel(viewModel: CharacterListViewModel): ViewModel

}