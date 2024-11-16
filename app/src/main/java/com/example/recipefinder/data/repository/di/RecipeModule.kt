package com.example.recipefinder.data.repository.di

import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.data.repository.recipe.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RecipeModule {

    @Binds
    @Singleton
    abstract fun bindsRecipeRepository(
        recipeRepository: RecipeRepositoryImpl
    ): RecipeRepository
}
