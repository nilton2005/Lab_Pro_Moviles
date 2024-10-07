package com.example.labo_09.data

import com.google.gson.annotations.SerializedName
data class CharacterResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Character>

)

// not util
data class  Info(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val pre: String?

)

data class Character(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
)
