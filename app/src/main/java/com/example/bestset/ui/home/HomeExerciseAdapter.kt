package com.example.bestset.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestset.R
import com.example.bestset.data.ExerciseContent

class HomeExerciseAdapter(val clickListener : OnClickListener) : RecyclerView.Adapter<HomeExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): HomeExerciseAdapter.ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun getItemCount() = 9

    override fun onBindViewHolder(holder: HomeExerciseAdapter.ExerciseViewHolder, position: Int) {
        holder.exerciseName.text = "New Exercise"
        holder.itemView.setOnClickListener{
            clickListener.onClick()
        }
    }

    class ExerciseViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var exerciseName : TextView

        init {
           exerciseName = itemView.findViewById(R.id.exercise_list_text)
        }
    }

    class OnClickListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }



}