package com.example.bestset.ui.records.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestset.data.WeightDatabase

class InputDialogsViewModelFactory(val datasource : WeightDatabase) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputDialogsViewModel::class.java)) {
            return InputDialogsViewModel(datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}