package com.tushant.recipesearchapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_recipe")
data class FavouriteRecipe(
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val image: String?,
    val readyInMinutes: Int?
) : Serializable
