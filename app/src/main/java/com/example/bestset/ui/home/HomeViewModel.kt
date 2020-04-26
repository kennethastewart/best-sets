package com.example.bestset.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(val datasource : ExerciseDatabase, val application : Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val allExerciseData = datasource.exerciseDatabaseDao.getAllExerciseData()
    val exerciseGroups = groupExercisesByName(allExerciseData)

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun groupExercisesByName(allExerciseData: LiveData<List<ExerciseContent>>): LiveData<MutableList<String>> {
        return Transformations.map(allExerciseData) { exercises ->
            val exerciseGroupNames = mutableListOf<String>()
            exercises.forEach() {
                val exerciseName = it.exercise.toString()
                if (!exerciseGroupNames.contains(exerciseName)) {
                    exerciseGroupNames.add(exerciseName)
                }
            }
            exerciseGroupNames
        }
    }
}