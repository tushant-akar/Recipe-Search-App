package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.Equipment
import com.tushant.recipesearchapp.data.model.FavouriteRecipe
import com.tushant.recipesearchapp.data.model.Ingredients
import com.tushant.recipesearchapp.data.model.SimiliarRecipeResponse
import com.tushant.recipesearchapp.databinding.FragmentRecipeBottomSheetBinding
import com.tushant.recipesearchapp.ui.adapter.bottomSheetFragment.SimilarAdapter
import com.tushant.recipesearchapp.ui.adapter.homeFragment.AllRecipesAdapter
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.EquipmentsAdapter
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.IngredientsAdapter
import com.tushant.recipesearchapp.viewModel.BottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_recipe_bottom_sheet) {
    private var _binding: FragmentRecipeBottomSheetBinding? = null
    private var isFavourite = false
    private val args: RecipeBottomSheetFragmentArgs by navArgs()
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerViews
        setupRecyclerViews()

        binding.mainContainer.visibility = View.VISIBLE
        binding.ingredientsContainer.visibility = View.GONE
        binding.recipeContainer.visibility = View.GONE
        binding.similiarRecipeContainer.visibility = View.GONE

        bottomSheetViewModel.fetchRecipeInformation(args.searchResult.id!!)
        bottomSheetViewModel.fetchIngredients(args.searchResult.id!!)
        bottomSheetViewModel.fetchNutrition(args.searchResult.id!!)
        bottomSheetViewModel.fetchEquipment(args.searchResult.id!!)
        bottomSheetViewModel.fetchSimilarRecipes(args.searchResult.id!!)

        var favouriteRecipe: FavouriteRecipe? = null

        binding.backBtn.setOnClickListener {
            dismiss()
        }

        bottomSheetViewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let {
                binding.recipeName.text = recipe.title
                binding.recipeTime.text = "${recipe.readyInMinutes} min"
                binding.servings.text = recipe.servings.toString()
                binding.recipeSummary.text = htmlToPlainText(recipe.summary ?: "")
                binding.recipeInstructions.text = htmlToPlainText(recipe.instructions ?: "")
                binding.price.text = recipe.pricePerServing.toString()
                binding.recipeItemImage.load(recipe.image)

                favouriteRecipe = FavouriteRecipe(
                    id = recipe.id,
                    image = recipe.image,
                    title = recipe.title,
                    readyInMinutes = recipe.readyInMinutes
                )
            }
        }


        binding.fabBtn.setOnClickListener {
            if (isFavourite) {
                if (favouriteRecipe != null) {
                    bottomSheetViewModel.deleteRecipe(favouriteRecipe!!)
                }
                Toast.makeText(
                    requireContext(),
                    "Recipe removed from favourites.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.fabBtn.setImageResource(R.drawable.ic_heart_unselected)
            } else {
                if (favouriteRecipe != null) {
                    bottomSheetViewModel.insertRecipe(favouriteRecipe!!)
                }
                Toast.makeText(requireContext(), "Recipe added to favourites.", Toast.LENGTH_SHORT)
                    .show()
                binding.fabBtn.setImageResource(R.drawable.ic_heart_selected)
            }
            isFavourite = !isFavourite
        }

        bottomSheetViewModel.ingredients.observe(viewLifecycleOwner) { ingredients ->
            ingredients?.let {
                (binding.recyclerViewIngredients.adapter as IngredientsAdapter).setIngredients(it)
            }
        }

        bottomSheetViewModel.equipment.observe(viewLifecycleOwner) { equipment ->
            equipment?.let {
                (binding.recyclerViewEquipments.adapter as EquipmentsAdapter).setEquipments(it)
            }
        }

        // Set click listeners
        binding.getIngredientBtn.setOnClickListener {
            animateContainerChange(binding.mainContainer, binding.ingredientsContainer)
            binding.getSimiliarRecipeBtn.visibility = View.VISIBLE
        }

        binding.getRecipeBtn.setOnClickListener {
            animateContainerChange(binding.ingredientsToogle, binding.recipeContainer)
        }

        binding.getSimiliarRecipeBtn.setOnClickListener {
            animateContainerChange(binding.recipeToogle, binding.similiarRecipeContainer)
            binding.getSimiliarRecipeBtn.visibility = View.GONE
        }

        binding.ingredientsHeader.setOnClickListener {
            if (binding.ingredientsToogle.visibility == View.GONE) {
                binding.ingredientsToogle.visibility = View.VISIBLE
                binding.recipeContainer.visibility = View.GONE
                binding.similiarRecipeContainer.visibility = View.GONE
            }

        }

        binding.recipeHeader.setOnClickListener {
            if (binding.recipeToogle.visibility == View.GONE) {
                binding.recipeToogle.visibility = View.VISIBLE
                binding.similiarRecipeContainer.visibility = View.GONE
            }
        }

        bottomSheetViewModel.similarRecipe.observe(viewLifecycleOwner, Observer { similarRecipes ->
            similarRecipes?.let {
                (binding.recyclerViewSimiliarRecipe.adapter as SimilarAdapter).setSimilarRecipes(it)
            }
        })
    }

    private fun setupRecyclerViews() {
        // Adapter setup for ingredientsRecyclerView
        val ingredientsAdapter = IngredientsAdapter(Ingredients(emptyList()))
        binding.recyclerViewIngredients.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.recyclerViewIngredients.adapter = ingredientsAdapter

        // Adapter setup for similarRecipeRecyclerView
        val similarRecipeAdapter = SimilarAdapter(SimiliarRecipeResponse())
        binding.recyclerViewSimiliarRecipe.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSimiliarRecipe.adapter = similarRecipeAdapter

        val equipmentAdapter = EquipmentsAdapter(Equipment(emptyList()))
        binding.recyclerViewEquipments.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.recyclerViewEquipments.adapter = equipmentAdapter
    }

    private fun animateContainerChange(hideContainer: View, showContainer: View) {
        hideContainer.visibility = View.GONE
        showContainer.visibility = View.VISIBLE
        showContainer.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_up
            )
        )
    }

    private fun htmlToPlainText(htmlText: String): String {
        val spanned: Spanned = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        return spanned.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}