package com.example.bestset

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDao
import com.example.bestset.data.ExerciseDatabase
import com.github.mikephil.charting.data.Entry
import org.hamcrest.EasyMock2Matchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class ExerciseDaoTests{
    private lateinit var database: ExerciseDatabase
    private lateinit var exerciseDao: ExerciseDao

    @Before
    fun initDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder( context, ExerciseDatabase::class.java ).build()
        exerciseDao = database.exerciseDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.bestset", appContext.packageName)
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val entry = ExerciseContent(1,"Dave55","push up", 10, false)
        exerciseDao.insert(entry)
        val byName = exerciseDao.getEntryByUsername("Dave55")
        Assert.assertEquals(byName.exercise, "push up")
    }
}



