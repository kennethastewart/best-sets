package com.example.bestset.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mikephil.charting.data.Entry
import java.util.*

@Entity(tableName = "exercise_records_table")
data class ExerciseContent(

    @PrimaryKey(autoGenerate = true)
    var setId:Long = 0L,

    @ColumnInfo(name = "user_name")
    var username:String,

    var exercise : String,

    @ColumnInfo(name = "exercise_volume")
    var exerciseVol: Long,

//    @ColumnInfo(name = "exercise_data")
//    var exerciseData: Entry,
//
//    var date: Date,

    var pr: Boolean
)