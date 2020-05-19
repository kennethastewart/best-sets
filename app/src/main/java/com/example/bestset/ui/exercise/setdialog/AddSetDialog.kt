package com.example.bestset.ui.exercise.setdialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.bestset.R
import com.example.bestset.data.ExerciseDatabase
import com.google.android.material.snackbar.Snackbar

class AddSetDialog() : DialogFragment() {

    lateinit var dialog :MaterialDialog
    var exerciseName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        if(getArguments() != null){
            arguments?.getString(getString(R.string.add_set_dialog_key))?.let{
                exerciseName = it
            }
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val application = requireNotNull(requireActivity()).application
        val datasource = ExerciseDatabase.getInstance(application)

        val viewmodel by lazy {
            val factory =
                AddSetViewModelFactroy(datasource)
            ViewModelProviders.of(this, factory).get<AddSetDialogViewModel>()
        }


        dialog = MaterialDialog(requireContext()).show {
            customView(R.layout.set_dialog)
            title(text = "Add a set of $exerciseName")
            positiveButton(R.string.dialog_submit) {
                val sets = findViewById<EditText>(R.id.sets_edit_text)
                val reps = findViewById<EditText>(R.id.reps_edit_text)

                if(exerciseName == ""){
                    Snackbar.make(requireView(), "Sorry Exercise Name was not found. Exercise was not saved.", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED).show()
                } else if (sets.editableText.toString() != "" && reps.editableText.toString() != "") {
                    val setInt = sets.editableText.toString().toInt()
                    val repInt = reps.editableText.toString().toInt()
                    val volume = repInt * setInt
                    viewmodel.volumeToBeAdded.value = volume
                    viewmodel.exerciseName.value = exerciseName
                    viewmodel.addSet()
                } else {
                    Snackbar.make(requireView(), "Set not added: Values were blank", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED).show()
                }

            }
        }
        return dialog
    }

}