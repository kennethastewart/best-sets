package com.example.bestset.login

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val _authenticationState = FirebaseUserLiveData()

    val authenticationState = Transformations.map(_authenticationState){ user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

}

