package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("ingredients")
    val ingredients: List<Ingredient?>?
) {
    data class Ingredient(
        @SerializedName("image")
        val image: String?,
        @SerializedName("name")
        val name: String?
    )
}