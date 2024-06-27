package com.tushant.recipesearchapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RandomRecipes(
    @SerializedName("recipes")
    val recipes: List<Recipe?>?
) {
    data class Recipe(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("imageType")
        val imageType: String?,
        @SerializedName("instructions")
        val instructions: String?,
        @SerializedName("pricePerServing")
        val pricePerServing: Double?,
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?,
        @SerializedName("servings")
        val servings: Int?,
        @SerializedName("sourceName")
        val sourceName: String?,
        @SerializedName("sourceUrl")
        val sourceUrl: String?,
        @SerializedName("spoonacularScore")
        val spoonacularScore: Double?,
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?,
        @SerializedName("summary")
        val summary: String?,
        @SerializedName("title")
        val title: String?,
    ) : Serializable {
        fun Recipe.toFavouriteRecipe(): FavouriteRecipe {
            return FavouriteRecipe(
                id = this.id,
                title = this.title,
                image = this.image,
                readyInMinutes = this.readyInMinutes
            )
        }
    }
}