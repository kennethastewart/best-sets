package com.example.bestset.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.example.bestset.MainActivity
import com.example.bestset.data.ExerciseDatabase
import com.example.bestset.databinding.FragmentRecordWeightBinding
import com.example.bestset.ui.records.dialog.InputDialogs
import com.github.mikephil.charting.data.LineData

class RecordWeightFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordWeightBinding.inflate(inflater)
        val application = requireNotNull(requireActivity()).application
        val datasource = ExerciseDatabase.getInstance(application)

        val viewModel by lazy{
            val factory = RecordWeightViewModelFactory(datasource)
            ViewModelProviders.of(this, factory).get<RecordWeightViewModel>()
        }
        val adapter = WeightAdapter()


        binding.recordWeightButton.setOnClickListener({
            InputDialogs()
                .show(requireFragmentManager(),"record weight")
        })

        viewModel.allWeightData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            prepareWeightChart(binding,  viewModel.prepareWeightChartData())
        })
        binding.weightRecycler.adapter = adapter
        (activity as MainActivity).supportActionBar?.title = "Body Weight"


        return binding.root
    }

    private fun prepareWeightChart(binding: FragmentRecordWeightBinding, weightChartData: LineData) {
        binding.weightChart.description.isEnabled = false
        binding.weightChart.data = weightChartData
        binding.weightChart.invalidate()

    }


}