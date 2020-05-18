package com.example.bestset.login

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class AuthViewModelUtil : ViewModel() {
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

    companion object User{
        val _authenticationData = FirebaseUserLiveData()

        val userName = Transformations.map(_authenticationData){ user ->
            if(user != null){
                user.displayName
            }else{
                "Default User"
            }
        }

        fun getUserName() : String{
            return userName.value.toString()
        }



    }






}

