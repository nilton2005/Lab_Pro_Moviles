package com.example.labo_09.data
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApiService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}
