package com.example.bestset.ui.addexercise

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import kotlinx.coroutines.*

class AddExerciseViewModel(val datasource : ExerciseDatabase , application: Application) : ViewModel( ) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val current_exercise = MutableLiveData<ExerciseContent?>()

    val exerciseName = MutableLiveData<String>()


    fun nameNewExercise(){
        uiScope.launch {
            val exercise = ExerciseContent()
                exercise.exercise = exerciseName.value.toString()
                insert(exercise)
        }
    }

    private suspend fun insert(newExercise : ExerciseContent){
        return withContext(Dispatchers.IO){
            datasource.exerciseDatabaseDao.insert(newExercise)
        }
    }


}