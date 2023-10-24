package com.priyanshub.mealsapp.repository

import com.priyanshub.mealsapp.data.models.MealsResponse
import com.priyanshub.mealsapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    suspend fun getSearchedMeals(search: String): Flow<Resource<MealsResponse>>
}