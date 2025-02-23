package com.example.recipefinder.data.repository.di

import com.example.recipefinder.data.repository.community.CommunityRepository
import com.example.recipefinder.data.repository.community.CommunityRepositoryImpl
import com.example.recipefinder.data.repository.community.PostCommentsRepository
import com.example.recipefinder.data.repository.community.PostCommentsRepositoryImpl
import com.example.recipefinder.data.repository.recipe.RecipeRepository
import com.example.recipefinder.data.repository.recipe.RecipeRepositoryImpl
import com.example.recipefinder.data.repository.tip.RecipeTipsRepository
import com.example.recipefinder.data.repository.tip.RecipeTipsRepositoryImpl
import com.example.recipefinder.data.repository.user.UserRepository
import com.example.recipefinder.data.repository.user.UserRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindsCommunityRepository(
        communityRepository: CommunityRepositoryImpl
    ): CommunityRepository

    @Binds
    @Singleton
    abstract fun bindsPostCommentsRepository(
        postCommentsRepository: PostCommentsRepositoryImpl
    ): PostCommentsRepository


    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}
