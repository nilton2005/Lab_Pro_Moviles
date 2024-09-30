package com.example.lab_09.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    suspend fun getUserPosts(): List<PostModel>
    @GET("posts/{id}")
    suspend fun getUsserPostById(@Path("id") id: Int) : PostModel


}