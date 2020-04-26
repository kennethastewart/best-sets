package com.example.bestset.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bestset.R
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val datasource = ExerciseDatabase.getInstance(application)

        val viewModelFactory = HomeViewModelFactory(datasource, application)
        homeViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.viewmodel = homeViewModel
        binding.fabplus.setOnClickListener { view ->
            this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToAddExerciseFragment())
//            Snackbar.make(view, "Add New Item", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
        val adapter = HomeExerciseAdapter(HomeExerciseAdapter.OnClickListener{
            this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToExerciseFragment())
        })
        binding.setLifecycleOwner(this)
//        homeViewModel.allExerciseData.observe(this, Observer {
//            it?.let{
//                adapter.exercises = it
//            }
//        })
        homeViewModel.exerciseGroups.observe(this, Observer {
            it?.let {
                adapter.exercises = it
            }
        })
        binding.exercisesRecycleview.adapter = adapter
        return binding.root
    }
}
