package com.priyanshub.mealsapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priyanshub.mealsapp.data.models.Meal
import com.priyanshub.mealsapp.databinding.ItemMealLayoutBinding
import kotlin.random.Random

class MealsAdapter(
    private var list: ArrayList<Meal>,
    private val listener: MealClickListener
): RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {
    inner class MealViewHolder(val binding: ItemMealLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    fun insertList(list: ArrayList<Meal>) {
        this.list.clear()
        this.list = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = list[position]
        Glide.with(holder.itemView.context).load(meal.strMealThumb)
            .into(holder.binding.ivMealImage)

        holder.binding.apply {
            tvMealName.text = meal.strMeal.toString()
            val rate = "Rating - ${rating()}"
            tvMealRating.text = rate
            holder.itemView.setOnClickListener {
                listener.onMealClick(meal)
            }
        }
    }
    private fun rating(): Double{
        val randomDecimal = Random.nextDouble(2.5, 5.0)
        return String.format("%.2f", randomDecimal).toDouble()
    }

}

interface MealClickListener {
    fun onMealClick(meal: Meal)
}