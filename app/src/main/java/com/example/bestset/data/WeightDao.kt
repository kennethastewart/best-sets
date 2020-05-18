package com.example.bestset.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeightDao{

    @Insert
    fun insert(userWeight: UserWeight)

    @Update
    fun update(userWeight: UserWeight)

    @Query("DELETE FROM user_weight_records WHERE id = :id")
    fun deleteEntry(id : String)

    @Query("SELECT * FROM user_weight_records  WHERE user_name = :userName ORDER BY date DESC;"  )
    fun getUserWeighRecords(userName : String) : LiveData<List<UserWeight>>

}