package com.example.bestset.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import kotlinx.coroutines.*

class ExerciseViewModel(val datasource: ExerciseDatabase, val exerciseName : String) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var exerciseData = MutableLiveData<List<ExerciseContent>?>()

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

    init {
        loadExercisesbyGroup()
    }

}