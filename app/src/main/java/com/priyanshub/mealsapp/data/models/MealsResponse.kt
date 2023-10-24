package com.priyanshub.mealsapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealsResponse(
    val meals: List<Meal>
) : Parcelable
