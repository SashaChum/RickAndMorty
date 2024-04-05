package com.chumikov.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chumikov.rickandmorty.R
import com.chumikov.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val repo = RickAndMortyRepositoryImpl()

            val allCharacters = repo.getCharacterList()
            val specificCharacter = repo.getCharacterDetails(2)
            val episodeList = repo.getCharacterEpisodes(listOf(1, 2, 3))

            Log.d("MainActivity", "$allCharacters")
            Log.d("MainActivity", "$specificCharacter")
            Log.d("MainActivity", "$episodeList")
        }
    }
}