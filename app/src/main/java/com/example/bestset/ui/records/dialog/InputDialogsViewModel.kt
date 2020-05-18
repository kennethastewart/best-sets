package com.example.bestset.ui.records.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.UserWeight
import com.example.bestset.data.WeightDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class InputDialogsViewModel(val datasource: WeightDatabase) : ViewModel(){


    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val username = getCurrentUsername()
    val latestWeight = MutableLiveData<Float>()

    fun addWeightToDatabase(){
        uiScope.launch {
            latestWeight.value?.let {
                val weightEntry = UserWeight(it, 80f, username )
                insert(weightEntry)
            }
        }
    }

    suspend fun insert(weightEntry: UserWeight) {
        withContext(Dispatchers.IO){
            datasource.userweightDao.insert(weightEntry)
        }
    }

    private fun getCurrentUsername() : String{
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            return user.displayName.toString()
        }
        return "DEFAULT"
    }
}