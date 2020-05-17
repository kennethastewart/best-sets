package com.example.bestset.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bestset.databinding.FragmentRecordWeightBinding
import com.example.bestset.ui.sharedutils.InputDialogs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class RecordWeightFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentRecordWeightBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        binding.recordWeightButton.setOnClickListener({

            InputDialogs().show(requireFragmentManager(), "input")
        })



        return binding.root
    }
}