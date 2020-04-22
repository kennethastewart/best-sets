package com.example.bestset.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bestset.R
import com.example.bestset.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val binding = FragmentHomeBinding.inflate(inflater)
        binding
        val adapter = HomeExerciseAdapter(HomeExerciseAdapter.OnClickListener{
            this.findNavController().navigate(HomeFragmentDirections.actionNavHomeToExerciseFragment())
        })
        binding.exercisesRecycleview.adapter = adapter
        return binding.root
    }
}
