package com.example.bestset.ui.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bestset.R
import com.example.bestset.data.ExerciseContent
import com.example.bestset.ui.home.HomeExerciseAdapter

class ExerciseAdapter() : RecyclerView.Adapter<ExerciseAdapter.SetViewHolder>() {
    private var setData : List<ExerciseContent>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.SetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.set_item, parent, false)
        return ExerciseAdapter.SetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (setData == null){ 0 }else setData!!.size
    }

    var sets : List<ExerciseContent>?
    get() = setData
    set(newExercises){
        setData = newExercises
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ExerciseAdapter.SetViewHolder, position: Int) {
        val exerciseSet = setData!![position]
        holder.exerciseName.text = exerciseSet.exercise
    }


    class SetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var exerciseName : TextView

        init {
            exerciseName = itemView.findViewById(R.id.exercise_set_name)
        }
    }
}
