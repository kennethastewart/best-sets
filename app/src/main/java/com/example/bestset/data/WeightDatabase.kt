package com.example.bestset.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserWeight::class], version = 1, exportSchema = false)
abstract class WeightDatabase : RoomDatabase(){

    abstract val userweightDao : WeightDao



    companion object {
        @Volatile
        private var INSTANCE : WeightDatabase? =  null
        val TABLE_NAME = "user_weight_database"


        fun getInstance(context: Context): WeightDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeightDatabase::class.java,
                        TABLE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}
