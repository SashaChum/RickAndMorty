package com.chumikov.rickandmorty.presentation

import android.app.Application
import android.util.Log
import com.chumikov.rickandmorty.di.DaggerApplicationComponent

class MyApplication : Application() {

    val component = DaggerApplicationComponent.factory().create(this)

    init {
        Log.d("My Inspection", "MyApplication init")
    }
}