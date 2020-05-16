package com.example.bestset.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.*

class ExerciseViewModel(val datasource: ExerciseDatabase, val exerciseName : String) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var exerciseData: LiveData<List<ExerciseContent>>
    var volumeToBeAdded = MutableLiveData<Int>()

    val _navigateHomeTrigger = MutableLiveData<Boolean>()

    val navigateHomeTrigger: LiveData<Boolean>
        get() = _navigateHomeTrigger

    fun addSet(){
        uiScope.launch {
            val exercise = ExerciseContent()
            exercise.exercise = exerciseName
            volumeToBeAdded.value?.let {
                exercise.exerciseVol = it
                insert(exercise)
            } }
    }

    fun prepareExerciseData(): List<ExerciseContent>?{
       val modifiedExerciseData = exerciseData.value?.reversed()?.dropLast(1)
        return modifiedExerciseData
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
        exerciseData = datasource.exerciseDatabaseDao.getExerciseGroup(exerciseName)
    }

    fun getPR() : Int{
        var pr = 0
        exerciseData.value?.forEach {
            if(it.exerciseVol > pr){ pr = it.exerciseVol }
        }
     return pr
    }

    fun getPRString() : String{
        val pr = getPR()
        return "Personal Best: $pr reps"
    }

    fun prepareExerciseChartData() : LineData{
        val entries = ArrayList<Entry>()
        var counter = 0f
        exerciseData.value?.forEach() {
            val setVol = it.exerciseVol.toFloat()
            entries.add(Entry(counter, setVol))
            counter++
        }
        val dataSet = LineDataSet(entries, "Test")
        val lineData = LineData(dataSet)
        return lineData
    }


}