package com.example.recipefinder.data.repository.user

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override fun getName(): String {
        return firebaseAuth.currentUser?.displayName!!
    }

    override fun getEmail(): String {
        return firebaseAuth.currentUser?.email!!
    }

    override fun getPhoneNumber(): String {
        return firebaseAuth.currentUser?.phoneNumber!!
    }

    override fun getPhoto(): Uri {
        return firebaseAuth.currentUser?.photoUrl!!
    }
}
