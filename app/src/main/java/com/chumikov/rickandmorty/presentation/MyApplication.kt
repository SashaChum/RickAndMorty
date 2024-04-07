package com.chumikov.rickandmorty.presentation

import android.app.Application
import com.chumikov.rickandmorty.di.DaggerApplicationComponent

class MyApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}