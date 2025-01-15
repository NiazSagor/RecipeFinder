package com.example.recipefinder.data.repository.di

import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.data.repository.recipe.RecipeRepositoryImpl
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import com.example.recipefinder.data.repository.tip.RecipeTipsRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindsRecipeTipsRepository(
        recipeTipsRepository: RecipeTipsRepositoryImpl
    ): RecipeTipsRepository
}
