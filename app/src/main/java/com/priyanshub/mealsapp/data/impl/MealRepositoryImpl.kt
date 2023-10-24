package com.priyanshub.mealsapp.data.impl

import android.util.Log
import com.priyanshub.mealsapp.data.api.MealsApiService
import com.priyanshub.mealsapp.data.models.MealsResponse
import com.priyanshub.mealsapp.repository.MealRepository
import com.priyanshub.mealsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MealRepositoryImpl(
    private val mealsApiService: MealsApiService
): MealRepository {
    override suspend fun getSearchedMeals(search: String): Flow<Resource<MealsResponse>> {
        return flow{
            emit(Resource.Loading())
            val response  = mealsApiService.getSearchedMeals(search)
            if (response.isSuccessful && response.body() != null){
                Log.d("PRI", "getSearchedMealsRepo: ${response.body()}")
                emit(Resource.Success(response.body()!!))
            }
            else{
                emit(Resource.Error("Error fetching meals"))
            }
        }.catch{
            emit(Resource.Error(it.localizedMessage ?: "Some error occurred in flow scope"))
        }
    }
}