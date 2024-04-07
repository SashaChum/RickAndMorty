package com.chumikov.rickandmorty.di

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [DomainModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: Fragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}