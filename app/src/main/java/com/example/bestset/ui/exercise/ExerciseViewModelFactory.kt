package com.example.bestset.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.ui.addexercise.AddExerciseViewModel

class ExerciseViewModelFactory(
    val datasource : ExerciseDatabase,
    val exerciseName : String
) :  ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(datasource, exerciseName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}