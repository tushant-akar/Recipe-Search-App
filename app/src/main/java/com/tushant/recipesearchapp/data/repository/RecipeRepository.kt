package com.tushant.recipesearchapp.data.repository

import com.tushant.recipesearchapp.data.remote.APIHelper
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val apiHelper: APIHelper
) {
    suspend fun searchRecipes(query: String) = apiHelper.searchRecipes(query)

    suspend fun getRandomRecipes(number: Int) = apiHelper.getRandomRecipes(number)

    suspend fun getRecipeInformation(id: Int) = apiHelper.getRecipeInformation(id)

    suspend fun getSimilarRecipes(id: Int) = apiHelper.getSimilarRecipes(id)

    suspend fun getRecipeEquipment(id: Int) = apiHelper.getRecipeEquipment(id)

    suspend fun getRecipeIngredients(id: Int) = apiHelper.getRecipeIngredients(id)

    suspend fun getRecipeNutrition(id: Int) = apiHelper.getRecipeNutrition(id)
}