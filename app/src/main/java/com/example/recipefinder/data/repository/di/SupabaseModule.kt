package com.example.recipefinder.data.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.resumable.MemoryResumableCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    // TODO: move the url and key to secrets
    @Provides
    @Singleton
    fun providesSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://kxspxycwnuemptjvvgbk.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imt4c3B4eWN3bnVlbXB0anZ2Z2JrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzgxNjE3MjYsImV4cCI6MjA1MzczNzcyNn0.f12nkXNmnRtur4vay2MjZhIuffK31KeNc85jg79RRCY"
        ) {
            install(Storage) {
                resumable {
                    cache = MemoryResumableCache()
                }
            }
        }
    }
}
