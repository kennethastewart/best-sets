package com.example.bestset.ui.exercise

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseViewModelTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ExerciseViewModel
    private lateinit var exerciseDatabase: ExerciseDatabase
    private lateinit var exerciseData : List<ExerciseContent>
    val STAR_JUMPS = "Star Jumps"

    @Before
    fun prepareViewmodel(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        exerciseDatabase = Room.inMemoryDatabaseBuilder( context, ExerciseDatabase::class.java ).build()
        val exercise1 = ExerciseContent("Billy", STAR_JUMPS, 0)
        val exercise2 = ExerciseContent("Billy", STAR_JUMPS, 100)
        val exercise3 = ExerciseContent("Billy", STAR_JUMPS, 300)
        val exercise4 = ExerciseContent("Billy", STAR_JUMPS, 50)
        exerciseDatabase.exerciseDatabaseDao.insert(exercise1)
        exerciseDatabase.exerciseDatabaseDao.insert(exercise2)
        exerciseDatabase.exerciseDatabaseDao.insert(exercise3)
        exerciseDatabase.exerciseDatabaseDao.insert(exercise4)
        viewModel = ExerciseViewModel(exerciseDatabase, STAR_JUMPS)
        exerciseData = viewModel.exerciseData.getOrAwaitValue()
    }

    @After
    fun closeDb() {
    exerciseDatabase.close()
    }

    @Test
    fun getExerciseData() {
        val size = exerciseData.size
        assertThat(size, `is`(4))
    }


    @Test
    fun prepareExerciseData() {
        val modifiedExerciseData = viewModel.prepareExerciseData()
        val size = modifiedExerciseData?.size
        assertThat(size, `is`(3))
    }


    @Test
    fun getPR() {
        val pr = viewModel.getPR()
        assertThat(pr, `is`(300))
    }

    @Test
    fun getPRString() {
        val pr = viewModel.getPRString()
        assertThat(pr, `is`("Personal Best: 300 reps"))
    }

    @Test
    fun getExerciseName() {
        val exerciseName = viewModel.exerciseName
        assertThat(exerciseName, `is`(STAR_JUMPS))
    }
}