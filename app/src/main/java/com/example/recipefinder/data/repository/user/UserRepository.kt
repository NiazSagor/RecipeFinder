package com.example.recipefinder.data.repository.user

import android.net.Uri

interface UserRepository {
    fun getName(): String
    fun getEmail(): String
    fun getPhoneNumber(): String
    fun getPhoto(): Uri
}
