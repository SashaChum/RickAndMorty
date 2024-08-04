package com.chumikov.rickandmorty.di

import com.chumikov.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.chumikov.rickandmorty.domain.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindRepository(impl: RickAndMortyRepositoryImpl): RickAndMortyRepository

}