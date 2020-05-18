package com.example.bestset.login

import com.google.firebase.auth.FirebaseAuth

class AuthUtil {

    companion object{
        fun getCurrentUsername() : String{
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null){
                return user.displayName.toString()
            }
            return "DEFAULT"
        }
    }


}