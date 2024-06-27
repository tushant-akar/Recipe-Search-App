package com.tushant.recipesearchapp.ui.adapter.searchFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tushant.recipesearchapp.data.model.SearchRecipeResponse
import com.tushant.recipesearchapp.databinding.SearchListItemBinding

class SearchResultAdapter(
    private var searchResults: SearchRecipeResponse,
    private val onItemClick: (SearchRecipeResponse.Result) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: SearchRecipeResponse.Result) {
            binding.searchResult.text = searchResult.title
            binding.root.setOnClickListener {
                searchResults.results!![adapterPosition]?.let { it1 -> onItemClick(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = searchResults.results?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        searchResults.results!![position]?.let { holder.bind(it) }
    }

    fun updateData(newSearchResults: SearchRecipeResponse) {
        searchResults.results = newSearchResults.results
        notifyDataSetChanged()
    }
}