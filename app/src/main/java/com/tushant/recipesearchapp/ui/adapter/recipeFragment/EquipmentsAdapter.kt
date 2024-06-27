package com.tushant.recipesearchapp.ui.adapter.recipeFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tushant.recipesearchapp.R
import com.tushant.recipesearchapp.data.model.Equipment

class EquipmentsAdapter(private var equipment: Equipment) :
    RecyclerView.Adapter<EquipmentsAdapter.EquipmentsViewHolder>() {

    inner class EquipmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val equipmentName = itemView.findViewById<TextView>(R.id.ingredientName)
        val equipmentImage = itemView.findViewById<ImageView>(R.id.ingredient_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredients_item, parent, false)
        return EquipmentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentsViewHolder, position: Int) {
        val ingredient = equipment.equipment?.get(position)
        holder.equipmentName.text = ingredient?.name
        if (ingredient?.image != null) {
            holder.equipmentImage.load(ingredient.image)
            Log.d("EquipmentsAdapter", "Image found for ${ingredient.name}, ${ingredient.image}")
        }
        else {
            Log.d("EquipmentsAdapter", "Image not found for ${ingredient?.name}")
        }


    }

    override fun getItemCount() = equipment.equipment?.size ?: 0

    fun setEquipments(equipment: Equipment) {
        this.equipment = equipment
        notifyDataSetChanged()
    }
}