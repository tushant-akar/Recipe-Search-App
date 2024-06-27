package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.RandomRecipes
import com.tushant.recipesearchapp.databinding.FragmentHomeBinding
import com.tushant.recipesearchapp.ui.adapter.homeFragment.AllRecipesAdapter
import com.tushant.recipesearchapp.ui.adapter.homeFragment.PopularRecipeAdapter
import com.tushant.recipesearchapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var recipesAdapter: AllRecipesAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setupRecyclerView()

        val name = auth.currentUser?.displayName
        binding.greetingText.text = "\uD83D\uDC4B Hey $name"

        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        recipesAdapter.setOnItemClickListener { recipe ->
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeFragment(
                recipe = recipe,
                favouriteRecipe = null
            )
            findNavController().navigate(action)
        }

        homeViewModel.popularRecipes.observe(viewLifecycleOwner, Observer { popularRecipe ->
            popularRecipe?.let {
                (binding.recyclerViewPopular.adapter as PopularRecipeAdapter).setRecipes(it)
            }
        })

        homeViewModel.allRecipes.observe(viewLifecycleOwner, Observer { allRecipes ->
            allRecipes?.let {
                (binding.recyclerViewRecipes.adapter as AllRecipesAdapter).setAllRecipes(it)
            }
        })

        homeViewModel.fetchPopularRecipes(10)
        homeViewModel.fetchAllRecipes(50)
    }

    private fun setupRecyclerView() {
        recipesAdapter = AllRecipesAdapter(RandomRecipes(emptyList()))
        binding.recyclerViewRecipes.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.recyclerViewPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = PopularRecipeAdapter(RandomRecipes(emptyList()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}