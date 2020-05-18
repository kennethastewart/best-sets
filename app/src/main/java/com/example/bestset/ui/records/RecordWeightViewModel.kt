package com.example.bestset.ui.records

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.UserWeight
import com.example.bestset.data.WeightDatabase
import com.example.bestset.login.AuthViewModelUtil
import kotlinx.coroutines.*

class RecordWeightViewModel(val datasource: WeightDatabase) : ViewModel(){


    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val latestWeight = MutableLiveData<Float>()

    fun addWeightToDatabase(){
        uiScope.launch {
            latestWeight.value?.let {
                val weightEntry = UserWeight(it, 80f, "DEFAULT" )
                insert(weightEntry)
            }

        }

    }

    suspend fun insert(weightEntry: UserWeight) {
        withContext(Dispatchers.IO){
            datasource.userweightDao.insert(weightEntry)
        }
    }
}