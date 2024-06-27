package com.tushant.recipesearchapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tushant.recipesearchapp.data.model.FavouriteRecipe

@Database(entities = [FavouriteRecipe::class], version = 1, exportSchema = false)
abstract class FavouriteRecipeDatabase : RoomDatabase() {
    abstract fun favouriteRecipeDao(): FavouriteRecipeDao
}