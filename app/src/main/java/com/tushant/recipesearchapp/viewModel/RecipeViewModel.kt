package com.tushant.recipesearchapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushant.recipesearchapp.data.model.Equipment
import com.tushant.recipesearchapp.data.model.FavouriteRecipe
import com.tushant.recipesearchapp.data.model.Ingredients
import com.tushant.recipesearchapp.data.model.Nutrition
import com.tushant.recipesearchapp.data.model.RandomRecipes
import com.tushant.recipesearchapp.data.remote.APIHelper
import com.tushant.recipesearchapp.data.repository.FavouriteRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val apiHelper: APIHelper,
    private val recipeRepository: FavouriteRecipeRepository
) : ViewModel() {

    private val _recipe = MutableLiveData<RandomRecipes.Recipe>()
    val recipe: LiveData<RandomRecipes.Recipe> get() = _recipe

    private val _ingredients = MutableLiveData<Ingredients>()
    val ingredients: LiveData<Ingredients> get() = _ingredients

    private val _nutrition = MutableLiveData<List<Nutrition>>()
    val nutrition: LiveData<List<Nutrition>> get() = _nutrition

    private val _equipment = MutableLiveData<Equipment>()
    val equipment: LiveData<Equipment> get() = _equipment

    fun fetchRecipeInformation(recipeId: Int) {
        viewModelScope.launch {
            try {
                val recipeInfo = apiHelper.getRecipeInformation(recipeId)
                _recipe.postValue(recipeInfo)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error fetching recipe information ${e.message}")
            }
        }
    }

    fun fetchIngredients(recipeId: Int) {
        viewModelScope.launch {
            try {
                val ingredients = apiHelper.getRecipeIngredients(recipeId)
                _ingredients.postValue(ingredients)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error fetching ingredients ${e.message}")
            }
        }
    }

    fun fetchNutrition(recipeId: Int) {
        viewModelScope.launch {
            try {
                val nutrition = apiHelper.getRecipeNutrition(recipeId)
                _nutrition.postValue(nutrition)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error fetching nutrition ${e.message}")
            }
        }
    }

    fun fetchEquipment(recipeId: Int) {
        viewModelScope.launch {
            try {
                val equipment = apiHelper.getRecipeEquipment(recipeId)
                _equipment.postValue(equipment)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error fetching equipment ${e.message}")
            }
        }
    }

    fun insertRecipe(recipe: FavouriteRecipe) {
        viewModelScope.launch {
            try {
                recipeRepository.insert(recipe)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error inserting recipe ${e.message}")
            }
        }
    }

    fun deleteRecipe(recipe: FavouriteRecipe) {
        viewModelScope.launch {
            try {
                recipeRepository.delete(recipe)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RecipeViewModel", "Error deleting recipe ${e.message}")
            }
        }
    }
}