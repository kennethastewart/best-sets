package com.example.bestset.ui.records

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.bestset.R

class InputDialogs(val viewModel: RecordWeightViewModel) : DialogFragment() {
    lateinit var dialog: MaterialDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog = MaterialDialog(requireContext())
            .show {
            input(allowEmpty = false, hint = "Add a New Weight"  )
            positiveButton(R.string.dialog_submit){
                val weight = getInputField().editableText.toString().toFloat()
                viewModel.latestWeight.value = weight
                viewModel.addWeightToDatabase()
            }
        }


        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStop() {
        if(dialog != null) {
            dialog.dismiss()
        }
        super.onStop()
    }
}