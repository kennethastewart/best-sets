package com.example.bestset.ui.sharedutils

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.bestset.R
import com.example.bestset.ui.records.RecordWeightViewModel

class InputDialogs(val viewModel: RecordWeightViewModel) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = MaterialDialog(requireContext())

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