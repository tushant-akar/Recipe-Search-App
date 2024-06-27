package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.Equipment
import com.tushant.recipesearchapp.data.model.FavouriteRecipe
import com.tushant.recipesearchapp.data.model.Ingredients
import com.tushant.recipesearchapp.data.model.RandomRecipes
import com.tushant.recipesearchapp.databinding.FragmentRecipeBinding
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.EquipmentsAdapter
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.IngredientsAdapter
import com.tushant.recipesearchapp.viewModel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private val args: RecipeFragmentArgs by navArgs()
    private var isFavourite = false
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        var recipeId: Int? = null
        var favouriteRecipe: FavouriteRecipe? = null

        if(args.recipe != null) {
            val recipe = RandomRecipes.Recipe(
                id = args.recipe?.id,
                title = args.recipe?.title,
                image = args.recipe?.image,
                readyInMinutes = args.recipe?.readyInMinutes,
                servings = args.recipe?.servings,
                summary = args.recipe?.summary,
                instructions = args.recipe?.instructions,
                pricePerServing = args.recipe?.pricePerServing,
                imageType = args.recipe?.imageType,
                sourceName = args.recipe?.sourceName,
                sourceUrl = args.recipe?.sourceUrl,
                spoonacularScore = args.recipe?.spoonacularScore,
                spoonacularSourceUrl = args.recipe?.spoonacularSourceUrl
            )
            favouriteRecipe = FavouriteRecipe(
                id = recipe.id,
                image = recipe.image,
                title = recipe.title,
                readyInMinutes = recipe.readyInMinutes
            )
        } else {
            favouriteRecipe = args.favouriteRecipe
            isFavourite = true
        }

        if (args.recipe?.id != null) {
            recipeId = args.recipe?.id
        } else if (args.favouriteRecipe?.id != null) {
            recipeId = args.favouriteRecipe?.id
        }


        if (recipeId != null) {
            recipeViewModel.fetchRecipeInformation(recipeId)
            recipeViewModel.fetchIngredients(recipeId)
            recipeViewModel.fetchNutrition(recipeId)
            recipeViewModel.fetchEquipment(recipeId)
        }
        else {
            Toast.makeText(requireContext(), "Error in finding receipeId", Toast.LENGTH_SHORT).show()
            Log.e("RecipeFragment", "Error in finding receipeId")
        }

        binding.favBtn.setOnClickListener {
            if (isFavourite) {
                if (favouriteRecipe != null) {
                    recipeViewModel.deleteRecipe(favouriteRecipe)
                }
                Toast.makeText(
                    requireContext(),
                    "Recipe removed from favourites.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.favBtn.setImageResource(R.drawable.ic_heart_unselected)
            } else {
                if (favouriteRecipe != null) {
                    recipeViewModel.insertRecipe(favouriteRecipe)
                }
                Toast.makeText(requireContext(), "Recipe added to favourites.", Toast.LENGTH_SHORT)
                    .show()
                binding.favBtn.setImageResource(R.drawable.ic_heart_selected)
            }
            isFavourite = !isFavourite
        }

        recipeViewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.recipeName.text = recipe.title
                binding.recipeTime.text = "${recipe.readyInMinutes} min"
                binding.servings.text = recipe.servings.toString()
                binding.recipeSummary.text = recipe.summary
                binding.recipeInstructions.text = recipe.instructions
                binding.price.text = recipe.pricePerServing.toString()
                binding.recipeItemImage.load(recipe.image)
            }
        }

        recipeViewModel.ingredients.observe(viewLifecycleOwner) { ingredients ->
            ingredients?.let {
                (binding.recyclerViewIngredients.adapter as IngredientsAdapter).setIngredients(it)
            }
        }

        recipeViewModel.equipment.observe(viewLifecycleOwner) { equipment ->
            equipment?.let {
                (binding.recyclerViewEquipments.adapter as EquipmentsAdapter).setEquipments(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewIngredients.apply {
            adapter = IngredientsAdapter(Ingredients(emptyList()))
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.recyclerViewEquipments.apply {
            adapter = EquipmentsAdapter(Equipment(emptyList()))
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}