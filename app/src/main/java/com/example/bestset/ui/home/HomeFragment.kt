package com.example.bestset.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.bestset.R
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentHomeBinding

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
        }
        val adapter = HomeExerciseAdapter(HomeExerciseAdapter.OnClickListener{
            this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToExerciseFragment(it))
        })
        binding.setLifecycleOwner(this)

        homeViewModel.exerciseGroups.observe(this, Observer {
            it?.let {
                adapter.exercises = it
            }
        })
        binding.exercisesRecycleview.adapter = adapter
        return binding.root
    }
}
