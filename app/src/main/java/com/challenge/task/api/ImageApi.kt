package com.challenge.task.api

import com.challenge.task.api.model.ImageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageApi {
    // API endpoint for getting top images of the week based on search query
    @GET("gallery/search/top/week")
    suspend fun getImages(
        // Authorization header for API authentication
        @Header("Authorization") token: String,
        // Query parameter for the search term
        @Query("q") query: String,
        // Additional query parameter (for show only images)
        @Query("q_type") param2: String) : Response<ImageModel>
}