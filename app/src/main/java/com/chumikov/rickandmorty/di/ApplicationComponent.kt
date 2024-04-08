package com.chumikov.rickandmorty.di

import android.content.Context
import com.chumikov.rickandmorty.presentation.CharacterListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApiModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: CharacterListFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}