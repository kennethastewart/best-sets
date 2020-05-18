package com.example.bestset.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.UserWeight
import com.example.bestset.data.WeightDatabase
import com.example.bestset.login.AuthViewModelUtil
import com.firebase.ui.auth.data.model.User
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class RecordWeightViewModel(val datasource: WeightDatabase) : ViewModel(){


    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val latestWeight = MutableLiveData<Float>()


    val username = getCurrentUsername()
    val allWeightData = datasource.userweightDao.getUserWeighRecords(username)


    private fun getCurrentUsername() : String{
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            return user.displayName.toString()
        }
        return "DEFAULT"
    }

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

    fun prepareWeightChartData() : LineData {
        val entries = ArrayList<Entry>()
        var counter = 0f
        allWeightData.value?.forEach {
            val weight = it.userWeight
            entries.add(Entry(counter, weight))
            counter++
        }
        val dataSet = LineDataSet(entries, "Test")
        val lineData = LineData(dataSet)
        return lineData
    }


}