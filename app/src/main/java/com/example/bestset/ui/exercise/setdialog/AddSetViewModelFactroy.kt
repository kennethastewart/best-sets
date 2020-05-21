package com.example.bestset.ui.exercise.setdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestset.data.ExerciseDatabase

class AddSetViewModelFactroy(val datasource : ExerciseDatabase) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddSetDialogViewModel::class.java)) {
            return AddSetDialogViewModel(datasource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
