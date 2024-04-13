package com.chumikov.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.chumikov.rickandmorty.presentation.EpisodesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodeViewModelModule {

    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    @Binds
    fun bindEpisodeViewModel(viewModel: EpisodesViewModel): ViewModel
}