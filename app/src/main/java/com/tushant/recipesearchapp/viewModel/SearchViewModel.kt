package com.tushant.recipesearchapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushant.recipesearchapp.data.model.SearchRecipeResponse
import com.tushant.recipesearchapp.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private val _searchResults = MutableLiveData<SearchRecipeResponse>()
    val searchResults = _searchResults

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val results = recipeRepository.searchRecipes(query)
                _searchResults.postValue(results)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SearchViewModel", "Error searching recipes: ${e.message}")
            }
        }
    }
}