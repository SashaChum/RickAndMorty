package com.chumikov.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.chumikov.rickandmorty.presentation.EpisodeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodeViewModelModule {

    @IntoMap
    @ViewModelKey(EpisodeViewModel::class)
    @Binds
    fun bindEpisodeViewModel(viewModel: EpisodeViewModel): ViewModel
}