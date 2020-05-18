package com.example.bestset.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.login.FirebaseUserLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(val datasource : ExerciseDatabase) : ViewModel() {
    val allExerciseData = datasource.exerciseDatabaseDao.getAllExerciseData()
    val exerciseGroups = groupExercisesByName(allExerciseData)

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