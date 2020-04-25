package com.example.bestset.ui.addexercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bestset.R
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentAddExerciseBinding

class AddExerciseFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater,  container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val application = requireNotNull(this.activity).application
        val datasource = ExerciseDatabase.getInstance(application)
        val viewModelFactory = AddExerciseViewModelFactory(datasource, application)
        val addExerciseViewModel = ViewModelProviders.of(this, viewModelFactory).get(AddExerciseViewModel::class.java)

        val binding:FragmentAddExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_exercise, container, false )
        binding.viewmodel = addExerciseViewModel
        return binding.root
    }
}