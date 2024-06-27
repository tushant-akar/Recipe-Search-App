package com.tushant.recipesearchapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tushant.recipesearchapp.data.model.FavouriteRecipe

@Dao
interface FavouriteRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteRecipe: FavouriteRecipe)

    @Query("SELECT * FROM favourite_recipe")
    fun getAllFavouriteRecipes(): List<FavouriteRecipe>

    @Delete
    suspend fun delete(favouriteRecipe: FavouriteRecipe)
}