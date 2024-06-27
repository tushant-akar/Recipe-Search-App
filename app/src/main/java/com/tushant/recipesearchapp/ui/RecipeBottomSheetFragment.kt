package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.Equipment
import com.tushant.recipesearchapp.data.model.Ingredients
import com.tushant.recipesearchapp.data.model.SimiliarRecipeResponse
import com.tushant.recipesearchapp.databinding.FragmentRecipeBottomSheetBinding
import com.tushant.recipesearchapp.ui.adapter.bottomSheetFragment.SimilarAdapter
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.EquipmentsAdapter
import com.tushant.recipesearchapp.ui.adapter.recipeFragment.IngredientsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeBottomSheetBinding? = null
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

        // Set click listeners
        binding.getIngredientBtn.setOnClickListener {
            animateContainerChange(binding.mainContainer, binding.ingredientsContainer)
        }

        binding.getRecipeBtn.setOnClickListener {
            animateContainerChange(binding.ingredientsToogle, binding.recipeContainer)
        }

        binding.getSimiliarRecipeBtn.setOnClickListener {
            animateContainerChange(binding.recipeToogle, binding.similiarRecipeContainer)
            binding.similiarRecipeBtnToogle.visibility = View.GONE
        }

        binding.ingredientsHeader.setOnClickListener {
            toggleContainerVisibility(binding.ingredientsContainer)
            binding.recipeContainer.visibility = View.GONE
            binding.similiarRecipeContainer.visibility = View.GONE
        }

        binding.recipeHeader.setOnClickListener {
            toggleContainerVisibility(binding.recipeContainer)
            binding.similiarRecipeContainer.visibility = View.GONE
        }

        binding.similarRecipeHeader.setOnClickListener {
            toggleContainerVisibility(binding.similiarRecipeContainer)
        }
    }

    private fun setupRecyclerViews() {
        // Adapter setup for ingredientsRecyclerView
        val ingredientsAdapter = IngredientsAdapter(Ingredients(emptyList()))
        binding.recyclerViewIngredients.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerViewIngredients.adapter = ingredientsAdapter

        // Adapter setup for similarRecipeRecyclerView
        val similarRecipeAdapter = SimilarAdapter(SimiliarRecipeResponse())
        binding.recyclerViewSimiliarRecipe.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSimiliarRecipe.adapter = similarRecipeAdapter

        val equipmentAdapter = EquipmentsAdapter(Equipment(emptyList()))
        binding.recyclerViewEquipments.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.HORIZONTAL, false)
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

    private fun toggleContainerVisibility(container: View) {
        if (container.visibility == View.VISIBLE) {
            container.visibility = View.GONE
        } else {
            container.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}