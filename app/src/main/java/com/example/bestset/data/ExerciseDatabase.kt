package com.example.bestset.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExerciseContent::class], version = 1, exportSchema = false)
abstract class ExerciseDatabase : RoomDatabase(){

    abstract val exerciseDatabaseDao : ExerciseDao

    companion object{

        @Volatile
        private var INSTANCE: ExerciseDatabase? = null
        val TABLE_NAME = "exercise_history_database"

        fun getInstance(context : Context) : ExerciseDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
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