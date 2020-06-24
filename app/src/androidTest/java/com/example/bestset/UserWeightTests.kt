package com.example.bestset

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bestset.data.UserWeight
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserWeightTests {
    private lateinit var database : WeightDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeightDatabase::class.java).build()
    }

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun canSaveWeight_thenGetWeigh() = runBlockingTest{
        val weightEntry = UserWeight(85.4f, 80f, "BigBoy110")
        database.userweightDao.insert(weightEntry)
        val userEntryRequestLiveData = database.userweightDao.getUserWeighRecords("BigBoy110")
        val userEntryRequest = userEntryRequestLiveData.getOrAwaitValue()

        assertThat(userEntryRequest.size, `is`(1))
        assertThat(userEntryRequest[0].userWeight, `is`(85.4f))
        assertThat(userEntryRequest[0].targetWeight, `is`(80f))
    }




}