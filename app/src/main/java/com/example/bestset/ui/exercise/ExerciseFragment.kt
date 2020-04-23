package com.example.bestset.ui.exercise

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bestset.R
import com.example.bestset.databinding.FragmentExerciseBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class ExerciseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExerciseBinding.inflate(inflater)
        binding.viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        setupLineChart(binding)


        return binding.root
    }

    private fun setupLineChart(binding: FragmentExerciseBinding) {
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 2f))
        entries.add(Entry(2f, 3f))
        entries.add(Entry(3f, 4f))
        entries.add(Entry(4f, 3f))
        entries.add(Entry(5f, 3f))
        entries.add(Entry(6f, 3f))
        entries.add(Entry(7f, 1f))
        val dataSet = LineDataSet(entries, "Test")
        val lineData = LineData(dataSet)
        binding.chart.description.text = "Pull ups"
        binding.chart.description.textSize += 8
        binding.chart.data = lineData
//        binding.chart.description.textColor = context!!.getColor(R.color.colorAccent)
        binding.chart.invalidate()
    }

    fun recordExerciseResults(exerciseName : String, exerciseVolume: Int){



    }



}