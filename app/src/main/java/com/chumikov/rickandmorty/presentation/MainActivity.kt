package com.chumikov.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chumikov.rickandmorty.R



class MainActivity : AppCompatActivity() {


//    private val binding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        CoroutineScope(Dispatchers.IO).launch {
//            val repo = RickAndMortyRepositoryImpl()
//
//            val allCharacters = repo.getCharacterList()
//            val specificCharacter = repo.getCharacterDetails(2)
//            val episodeList = repo.getCharacterEpisodes(listOf(1, 2, 3))
//
//            Log.d("MainActivity", "$allCharacters")
//            Log.d("MainActivity", "$specificCharacter")
//            Log.d("MainActivity", "$episodeList")
//        }
    }
}