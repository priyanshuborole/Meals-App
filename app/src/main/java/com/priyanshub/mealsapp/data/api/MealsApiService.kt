package com.priyanshub.mealsapp.data.api

import com.priyanshub.mealsapp.data.models.MealsResponse
import com.priyanshub.mealsapp.utils.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApiService {

    @GET("api/json/v1/1/search.php")
    suspend fun getSearchedMeals(
        @Query("s") search: String
    ): Response<MealsResponse>

}