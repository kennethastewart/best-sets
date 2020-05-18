package com.example.bestset.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_weight_records")
data class UserWeight(

    @ColumnInfo(name = "user_weight")
    var userWeight: Float,

    @ColumnInfo(name = "target_weight")
    var targetWeight : Float,

    @ColumnInfo(name = "user_name")
    var username: String = "Default",

    var date: Long = System.currentTimeMillis(),

    @PrimaryKey
    var id:String = UUID.randomUUID().toString()
)