package com.example.bestset.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_records_table")
data class ExerciseContent(

    @PrimaryKey(autoGenerate = true)
    var setId:Long = 0L,

    @ColumnInfo(name = "user_name")
    var username:String = "Default",

    var exercise : String = "Default",

    @ColumnInfo(name = "exercise_volume")
    var exerciseVol: Long = 0L,

//    @ColumnInfo(name = "exercise_data")
//    var exerciseData: Entry,
//
//    var date: Date,

    @ColumnInfo
    var pr: Boolean = false
)