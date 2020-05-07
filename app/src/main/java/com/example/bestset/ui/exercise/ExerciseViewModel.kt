package com.example.bestset.ui.exercise

import androidx.lifecycle.LiveData
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

    var _closeDialogCheck = MutableLiveData<Boolean>()

    val closeDialogCheck: LiveData<Boolean>
        get() = _closeDialogCheck

    val _navigateHomeTrigger = MutableLiveData<Boolean>()

    val navigateHomeTrigger: LiveData<Boolean>
        get() = _navigateHomeTrigger

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

    fun deleteExerciseAndTriggerNavigateHome(){
        removeSet()
        _navigateHomeTrigger.value = true
    }

    fun dialogClosedSuccessfully(){
        _closeDialogCheck.value = false
    }

    fun removeSet(){
        uiScope.launch {
            remove()
        }
    }

    private suspend fun remove(){
        return withContext(Dispatchers.IO){
            datasource.exerciseDatabaseDao.deleteAllExcerisesWithName(exerciseName)
        }
    }

    fun navigatingDone(){
        _navigateHomeTrigger.value = false
    }

    init {
        loadExercisesbyGroup()
    }


}