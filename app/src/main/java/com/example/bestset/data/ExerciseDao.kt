package com.example.bestset.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface  ExerciseDao{

    @Insert
    fun insert(exercise : ExerciseContent)

    @Update
    fun update(exercise: ExerciseContent)

    @Query("DELETE FROM exercise_records_table")
    fun clear()

    @Query("DELETE FROM exercise_records_table WHERE exercise = :exerciseName")
    fun deleteAllExcerisesWithName(exerciseName : String)

    @Query("SELECT * FROM exercise_records_table")
    fun getAllExerciseData() : LiveData<List<ExerciseContent>>

    @Query("SELECT * FROM exercise_records_table WHERE user_name = :username ")
    fun getEntryByUsername(username: String) : ExerciseContent

    @Query("SELECT * FROM exercise_records_table WHERE exercise = :exerciseName")
    fun getExerciseGroup(exerciseName : String) : LiveData<List<ExerciseContent>>
}