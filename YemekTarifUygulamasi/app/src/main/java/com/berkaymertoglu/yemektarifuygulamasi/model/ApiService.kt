package com.berkaymertoglu.yemektarifuygulamasi.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/complexSearch")
    fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): Call<RecipeResponse>

    @GET("recipes/{id}/information")
    fun getRecipeDetails(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String
    ): Call<RecipeDetailResponse>

}



