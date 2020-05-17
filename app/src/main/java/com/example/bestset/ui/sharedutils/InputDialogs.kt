package com.example.bestset.ui.sharedutils

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.example.bestset.R
import com.example.bestset.data.UserWeight
import com.example.bestset.data.WeightDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class InputDialogs() : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val application = requireNotNull(this.activity).application
        val datasource = WeightDatabase.getInstance(application)
        var viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        MaterialDialog(requireContext()).show {
            input(allowEmpty = false, hint = "Add a New Weight"  )
            positiveButton(R.string.dialog_submit){
                val weight = getInputField().editableText.toString().toFloat()
                val newWeightEntry = UserWeight(weight, 19f, "BigBullba")
                uiScope.launch { addWeightToDatabase(datasource, newWeightEntry) }


            }

        }
        return super.onCreateDialog(savedInstanceState)
    }

    suspend fun addWeightToDatabase(
        datasource: WeightDatabase,
        newWeightEntry: UserWeight
    ) {
        withContext(IO){
            datasource.userweightDao.insert(newWeightEntry)

        }
    }






}