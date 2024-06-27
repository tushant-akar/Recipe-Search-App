package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.databinding.FragmentFavoriteBinding
import com.tushant.recipesearchapp.ui.adapter.favouriteFragment.FavouriteRecipeAdapter
import com.tushant.recipesearchapp.viewModel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteRecipeAdapter: FavouriteRecipeAdapter
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        favouriteViewModel.fetchFavouriteRecipes()

        favouriteRecipeAdapter.setOnItemClickListener { favouriteRecipe ->
            Log.d("FavoriteFragment", "Recipe clicked: ${favouriteRecipe.title}")
            val action = FavoriteFragmentDirections.actionFavoriteFragment2ToRecipeFragment(
                favouriteRecipe = favouriteRecipe,
                recipe = null
            )
            findNavController().navigate(action)
        }

        favouriteViewModel.favouriteRecipes.observe(viewLifecycleOwner) { favouriteRecipes ->
            favouriteRecipes?.let { recipes ->
                favouriteRecipeAdapter.setRecipes(recipes)
            }
        }
    }

    private fun setupRecyclerView() {
        favouriteRecipeAdapter = FavouriteRecipeAdapter(emptyList())
        binding.recyclerViewFavRecipes.apply {
            adapter = favouriteRecipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
