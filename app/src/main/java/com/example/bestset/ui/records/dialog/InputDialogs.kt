package com.example.bestset.ui.records.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.bestset.R
import com.example.bestset.data.WeightDatabase
import com.example.bestset.ui.records.RecordWeightViewModel
import com.example.bestset.ui.records.RecordWeightViewModelFactory

class InputDialogs() : DialogFragment() {
    lateinit var dialog: MaterialDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val application = requireNotNull(requireActivity()).application
        val datasource = WeightDatabase.getInstance(application)

        val viewModel by lazy {
            val factory =
                InputDialogsViewModelFactory(datasource)
            ViewModelProviders.of(this,factory).get<InputDialogsViewModel>()
        }

        dialog = MaterialDialog(requireContext())
            .show {
            input(allowEmpty = false, hint = "Add a New Weight"  )
            positiveButton(R.string.dialog_submit){
                val weight = getInputField().editableText.toString().toFloat()
                viewModel.latestWeight.value = weight
                viewModel.addWeightToDatabase()
            }
        }
        return dialog
    }



}