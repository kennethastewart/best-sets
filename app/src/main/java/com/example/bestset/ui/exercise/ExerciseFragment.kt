package com.example.bestset.ui.exercise

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.bestset.MainActivity
import com.example.bestset.R
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentExerciseBinding
import com.example.bestset.ui.exercise.setdialog.AddSetDialog
import com.github.mikephil.charting.components.Legend
import com.google.android.material.textfield.TextInputEditText


class ExerciseFragment : Fragment() {

    lateinit var sets: TextInputEditText
    lateinit var reps: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentExerciseBinding.inflate(inflater)
        val arguments = ExerciseFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        val datasource = ExerciseDatabase.getInstance(application)

        val viewModelFactory = ExerciseViewModelFactory(datasource, arguments.exerciseName)
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ExerciseViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = ExerciseAdapter()
        binding.setsRecycler.adapter = adapter
        binding.addSetButton.setOnClickListener(View.OnClickListener {
            val exerciseNameBundle = Bundle()
            exerciseNameBundle.putString(getString(R.string.add_set_dialog_key), arguments.exerciseName)
            val addSetDialog = AddSetDialog()
            addSetDialog.setArguments(exerciseNameBundle)
            addSetDialog.show(requireFragmentManager(), "Set Dialog")

        })
        viewModel.exerciseData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.sets = viewModel.prepareExerciseData()
                setupLineChart(binding, arguments, viewModel)
                binding.prText.text = viewModel.getPRString()
            }
        })

        viewModel.navigateHomeTrigger.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(ExerciseFragmentDirections.actionExerciseFragmentToNavHome())
                viewModel.navigatingDone()
            }
        })

        (activity as MainActivity).supportActionBar?.title = arguments.exerciseName
        return binding.root
    }

    private fun setupLineChart(
        binding: FragmentExerciseBinding,
        arguments: ExerciseFragmentArgs,
        viewModel: ExerciseViewModel
    ) {
        binding.chart.description.isEnabled = false
        binding.chart.isDoubleTapToZoomEnabled = true
        binding.chart.legend.form = Legend.LegendForm.CIRCLE
        binding.chart.legend.formSize = 10f
        binding.chart.legend.textSize = 16f
        binding.chart.legend.textColor = Color.WHITE

        binding.chart.data = viewModel.prepareExerciseChartData()

        if(binding.chart.legend.entries.size >= 1) {
            binding.chart.legend.entries[0].label = arguments.exerciseName
        }
        binding.chart.invalidate()
    }
}



