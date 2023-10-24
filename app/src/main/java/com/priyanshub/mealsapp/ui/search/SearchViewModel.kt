package com.priyanshub.mealsapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyanshub.mealsapp.data.models.MealsResponse
import com.priyanshub.mealsapp.repository.MealRepository
import com.priyanshub.mealsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mealRepository: MealRepository
): ViewModel() {

    private val _mealsStateFlow: MutableStateFlow<Resource<MealsResponse>> = MutableStateFlow(Resource.Loading())
    val mealsStateFlow : StateFlow<Resource<MealsResponse>> = _mealsStateFlow

    private val _mealNameStateFlow: MutableStateFlow<String> = MutableStateFlow("Chicken")
    val mealNameStateFlow : StateFlow<String> = _mealNameStateFlow

    fun getSearchedNews(search: String) = viewModelScope.launch(Dispatchers.IO) {
        mealRepository.getSearchedMeals(search).collectLatest{ mealsResponse->
            _mealsStateFlow.value = mealsResponse
        }
    }

    fun changeDefaultMealSearch(search: String){
        _mealNameStateFlow.value = search
    }

}