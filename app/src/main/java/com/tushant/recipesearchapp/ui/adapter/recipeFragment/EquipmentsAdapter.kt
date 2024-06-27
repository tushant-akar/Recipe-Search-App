package com.tushant.recipesearchapp.ui.adapter.recipeFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        val equipment = equipment.equipment?.get(position)
        holder.equipmentName.text = equipment?.name
        holder.equipmentImage.load("https://img.spoonacular.com/equipment_100x100/${equipment?.image}")

    }

    override fun getItemCount() = equipment.equipment?.size ?: 0

    fun setEquipments(equipment: Equipment) {
        this.equipment = equipment
        notifyDataSetChanged()
    }
}