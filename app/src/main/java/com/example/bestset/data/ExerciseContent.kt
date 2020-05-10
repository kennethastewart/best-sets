package com.example.bestset.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.util.*

@Entity(tableName = "exercise_records_table")
data class ExerciseContent(

    @ColumnInfo(name = "user_name")
    var username:String = "Default",

    var exercise : String = "Default",

    @ColumnInfo(name = "exercise_volume")
    var exerciseVol: Int = 0,

    @ColumnInfo(name = "exercise_data")
    var exerciseData: Long = 0,

    @ColumnInfo
    var pr: Boolean = false,

    var date: Long = System.currentTimeMillis(),

    @PrimaryKey
    var setId:String = UUID.randomUUID().toString()
)