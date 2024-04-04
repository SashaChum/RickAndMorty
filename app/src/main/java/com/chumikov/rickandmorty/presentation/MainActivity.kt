package com.chumikov.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.data.network.RickAndMortyApiFactory.rickAndMortyApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val allCharacters = rickAndMortyApi.getAllCharacters()
            val specificCharacter = rickAndMortyApi.getCharacterInfo(183)
            val episodeList = rickAndMortyApi.getEpisodeList(listOf(1, 2, 3).joinToString(","))
            Log.d("MainActivity", "$allCharacters")
            Log.d("MainActivity", "$specificCharacter")
            Log.d("MainActivity", "$episodeList")
        }
    }
}