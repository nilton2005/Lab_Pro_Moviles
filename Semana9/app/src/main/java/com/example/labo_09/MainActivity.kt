package com.example.labo_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.labo_09.data.CharacterApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private lateinit var characterApiService: CharacterApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configurando Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        characterApiService = retrofit.create(CharacterApiService::class.java)

        setContent {
            CharactersModule(characterApiService = characterApiService)
        }
    }
}