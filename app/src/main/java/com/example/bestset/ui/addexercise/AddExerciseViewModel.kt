package com.example.bestset.ui.addexercise

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import kotlinx.coroutines.*
import kotlin.reflect.typeOf

class AddExerciseViewModel(val datasource : ExerciseDatabase , application: Application) : ViewModel( ) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val current_exercise = MutableLiveData<ExerciseContent?>()

    val exerciseName = MutableLiveData<String>()

    private var _navigateToHomeCheck = MutableLiveData<Boolean>()

    val navigateToHomeCheck: LiveData<Boolean>
        get() = _navigateToHomeCheck

    fun doneNavigating(){
        _navigateToHomeCheck.value = false
    }


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

    fun addNewExerciseAndReturnToHome(){
        nameNewExercise()
        _navigateToHomeCheck.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}