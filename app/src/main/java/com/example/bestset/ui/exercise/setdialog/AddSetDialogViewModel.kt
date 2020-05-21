package com.example.bestset.ui.exercise.setdialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import kotlinx.coroutines.*

class AddSetDialogViewModel(val datasource: ExerciseDatabase) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var volumeToBeAdded = MutableLiveData<Int>()
    val exerciseName = MutableLiveData<String>()

    fun addSet(){
        uiScope.launch {
            val exercise = ExerciseContent()
            volumeToBeAdded.value?.let {
                exercise.exerciseVol = it
                exerciseName.value?.let {
                    exercise.exercise = it
                    insert(exercise)
                }
            }
        }
    }

    private suspend fun insert(newExercise : ExerciseContent){
        return withContext(Dispatchers.IO){
            datasource.exerciseDatabaseDao.insert(newExercise)
        }
    }



}