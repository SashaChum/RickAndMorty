package com.chumikov.rickandmorty.di

import com.chumikov.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.chumikov.rickandmorty.domain.RickAndMortyRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: RickAndMortyRepositoryImpl): RickAndMortyRepository

}