package com.tushant.recipesearchapp.data.repository

import com.tushant.recipesearchapp.data.local.FavouriteRecipeDatabase
import com.tushant.recipesearchapp.data.model.FavouriteRecipe
import javax.inject.Inject

class FavouriteRecipeRepository @Inject constructor(
    private val db: FavouriteRecipeDatabase
) {
    fun getAllFavouriteRecipes() = db.favouriteRecipeDao().getAllFavouriteRecipes()

    suspend fun insert(favouriteRecipe: FavouriteRecipe) {
        db.favouriteRecipeDao().insert(favouriteRecipe)
    }

    suspend fun delete(favouriteRecipe: FavouriteRecipe) {
        db.favouriteRecipeDao().delete(favouriteRecipe)
    }
}