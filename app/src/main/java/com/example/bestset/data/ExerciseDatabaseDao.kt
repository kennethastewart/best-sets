package com.example.bestset.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface  ExerciseDatabaseDao{

    @Insert
    fun insert(exercise : ExerciseContent)

    @Update
    fun update(exercise: ExerciseContent)

    @Query("DELETE FROM exercise_records_table")
    fun clear()
}