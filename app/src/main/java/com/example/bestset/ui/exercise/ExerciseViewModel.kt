package com.example.bestset.ui.exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.*

class ExerciseViewModel(val datasource: ExerciseDatabase, val exerciseName : String) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var exerciseData = MutableLiveData<List<ExerciseContent>?>()

    var volumeToBeAdded = MutableLiveData<Int>()

    fun loadExercisesbyGroup(){
        uiScope.launch {
            exerciseData.value = getGroupExercises()
        }
    }

    private suspend fun getGroupExercises() : List<ExerciseContent>{
        return withContext(Dispatchers.IO){
            datasource.exerciseDatabaseDao.getExerciseGroup(exerciseName)
        }
    }

    fun addSet(){
        uiScope.launch {
            val exercise = ExerciseContent()
            exercise.exercise = exerciseName
            volumeToBeAdded.value?.let {
                exercise.exerciseVol = it
                insert(exercise)
            } }

    }

    private suspend fun insert(newExercise : ExerciseContent){
        return withContext(Dispatchers.IO){
            datasource.exerciseDatabaseDao.insert(newExercise)
        }
    }



    init {
        loadExercisesbyGroup()
    }


}