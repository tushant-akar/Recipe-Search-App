package com.tushant.recipesearchapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.databinding.FragmentSearchBinding
import com.tushant.recipesearchapp.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = ArrayAdapter(requireContext(), R.layout.search_list_item, mutableListOf())
        binding.searchListView.adapter = searchAdapter

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.searchCancelBtn.setOnClickListener {
            binding.searchEditText.text.clear()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchViewModel.searchRecipes(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        searchViewModel.searchResults.observe(viewLifecycleOwner, Observer { searchResults ->
            searchResults?.let {
                val recipesTitles = it.results?.map { recipe -> recipe?.title }
                searchAdapter.apply {
                    clear()
                    addAll(recipesTitles as List<String>)
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}