package com.example.bestset

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ExerciseDaoTests{
    private lateinit var database: ExerciseDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder( context, ExerciseDatabase::class.java ).build()
    }

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun writeOneUserAndReadInList() {
        val entry = ExerciseContent("Dave55","push up", 10, 20)
        database.exerciseDatabaseDao.insert(entry)
        val byName = database.exerciseDatabaseDao.getEntryByUsername("Dave55")
        Assert.assertEquals(byName.exercise, "push up")
    }

    @Test
    fun itemCanBeUpdated() = runBlockingTest{
        val exerciseEntry = ExerciseContent("Jimmy", "sit ups", 20, 10, true )
        database.exerciseDatabaseDao.insert(exerciseEntry)
        exerciseEntry.exerciseVol = 30
        database.exerciseDatabaseDao.update(exerciseEntry)

        val loadedEntry = database.exerciseDatabaseDao.getEntryById(exerciseEntry.setId)

        assertThat(loadedEntry.exercise, `is`(exerciseEntry.exercise))
        assertThat(loadedEntry.exerciseVol, `is`(30))
        assertThat(loadedEntry.exerciseVol, `is`(not(20)))
    }

    @Test
    fun itemCanBeDeleted() = runBlockingTest {
        val entry1 = ExerciseContent("Tam", "Burpees", 100, 10, true)
        database.exerciseDatabaseDao.insert(entry1)
        var loadedData = database.exerciseDatabaseDao.getEntryById(entry1.setId)
        assertThat(loadedData.setId, `is`(entry1.setId))
        database.exerciseDatabaseDao.deleteExerciseById(entry1.setId)
        loadedData = database.exerciseDatabaseDao.getEntryById(entry1.setId)
        Assert.assertEquals(loadedData, null)

    }



}



