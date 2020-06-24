package com.example.bestset.ui.records

import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.login.AuthUtil
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth

class RecordWeightViewModel(val datasource: ExerciseDatabase) : ViewModel(){




    val username = AuthUtil.getCurrentUsername()
    val allWeightData = datasource.userweightDao.getUserWeighRecords(username)


    private fun getCurrentUsername() : String{
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            return user.displayName.toString()
        }
        return "DEFAULT"
    }



    fun prepareWeightChartData() : LineData {
        val entries = ArrayList<Entry>()
        var counter = 0f

        allWeightData.value?.reversed()?.forEach {
            val weight = it.userWeight
            entries.add(Entry(counter, weight))
            counter++
        }
        val dataSet = LineDataSet(entries, "Test")
        val lineData = LineData(dataSet)
        return lineData
    }


}