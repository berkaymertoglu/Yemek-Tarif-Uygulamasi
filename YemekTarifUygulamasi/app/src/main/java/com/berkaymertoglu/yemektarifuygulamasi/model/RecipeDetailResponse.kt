package com.berkaymertoglu.yemektarifuygulamasi.model

data class RecipeDetailResponse(
    val id: Int,
    val title: String,
    val image: String,
    val instructions: String
)
