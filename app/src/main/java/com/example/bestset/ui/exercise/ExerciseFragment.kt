package com.example.bestset.ui.exercise

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bestset.R
import com.example.bestset.data.ExerciseContent
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentExerciseBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.textfield.TextInputEditText


class ExerciseFragment : Fragment() {

    lateinit var sets: TextInputEditText
    lateinit var reps : TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {



        val binding = FragmentExerciseBinding.inflate(inflater)
        val arguments = ExerciseFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val datasource = ExerciseDatabase.getInstance(application)

        val viewModelFactory = ExerciseViewModelFactory(datasource ,arguments.exerciseName)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExerciseViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = ExerciseAdapter()
        binding.setsRecycler.adapter = adapter

        binding.addSetButton.setOnClickListener(View.OnClickListener {
            openAddSetDialog(inflater, arguments, viewModel)
        })
        viewModel.exerciseData.observe(this, Observer {
            it?.let {
                adapter.sets = it
                setupLineChart(binding, arguments, it)
            }
        })

        return binding.root
    }

    private fun openAddSetDialog(
        inflater: LayoutInflater,
        arguments: ExerciseFragmentArgs,
        viewModel: ExerciseViewModel
    ) {


        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
       val dialog = builder?.setView(inflater.inflate(R.layout.set_dialog, null))
            ?.setTitle(arguments.exerciseName)?.setMessage("Add a new session here:")
            ?.setPositiveButton("Add"){
                dialog, which ->  recordExerciseResults(viewModel)

            }?.
            create()
        dialog?.show()

        sets = dialog!!.findViewById<TextInputEditText>(R.id.sets_edit_text)
        reps = dialog!!.findViewById<TextInputEditText>(R.id.reps_edit_text)





    }

    private fun setupLineChart(
        binding: FragmentExerciseBinding,
        arguments: ExerciseFragmentArgs,
        exerciseData : List<ExerciseContent>
    ) {
        val entries = ArrayList<Entry>()
        var counter = 0f
        exerciseData.forEach(){
            val setVol = it.exerciseVol.toFloat()
            entries.add(Entry(counter, setVol))
            counter++
        }
//        entries.add(Entry(1f, 2f))
//        entries.add(Entry(2f, 3f))
//        entries.add(Entry(3f, 4f))
//        entries.add(Entry(4f, 3f))
//        entries.add(Entry(5f, 3f))
//        entries.add(Entry(6f, 3f))
//        entries.add(Entry(7f, 1f))
        val dataSet = LineDataSet(entries, "Test")
        val lineData = LineData(dataSet)
        binding.chart.description.text = arguments.exerciseName
        binding.chart.description.textSize += 8
        binding.chart.data = lineData
//        binding.chart.description.textColor = context!!.getColor(R.color.colorAccent)
        binding.chart.invalidate()
    }

    fun recordExerciseResults(
        viewModel: ExerciseViewModel
    ) {
        val setInt = sets?.editableText.toString().toInt()
        val repInt = reps?.editableText.toString().toInt()
        val volume =  repInt*setInt
        viewModel.volumeToBeAdded.value = volume
        viewModel.addSet()
    }



}



