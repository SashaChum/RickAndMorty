package com.chumikov.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chumikov.rickandmorty.domain.RickAndMortyApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val allCharacters = rickAndMortyApi.getAllCharacters()
            Log.d("MainActivity", "$allCharacters")
        }
    }
}