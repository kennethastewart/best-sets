package com.example.bestset.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestset.data.WeightDatabase

class RecordWeightViewModelFactory(
    val datasource : WeightDatabase
) :  ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordWeightViewModel::class.java)) {
            return RecordWeightViewModel(datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}