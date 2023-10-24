package com.priyanshub.mealsapp.di

import android.content.Context
import android.provider.SyncStateContract
import com.priyanshub.mealsapp.data.api.MealsApiService
import com.priyanshub.mealsapp.data.impl.MealRepositoryImpl
import com.priyanshub.mealsapp.repository.MealRepository
import com.priyanshub.mealsapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.cache.CacheInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMealsApiService(): MealsApiService {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(MealsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealRepository(api: MealsApiService): MealRepository {
        return MealRepositoryImpl(api)
    }

}