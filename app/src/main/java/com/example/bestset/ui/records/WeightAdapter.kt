package com.example.bestset.ui.records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bestset.data.UserWeight
import com.example.bestset.databinding.WeightItemBinding

class WeightAdapter : ListAdapter<UserWeight, WeightAdapter.UserWeightViewHolder>(DiffCallback) {

    class UserWeightViewHolder(private var binding : WeightItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userWeight: UserWeight){
            binding.recordedWeight.text = userWeight.userWeight.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserWeightViewHolder {
        return UserWeightViewHolder(WeightItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserWeightViewHolder, position: Int) {
        val userWeight = getItem(position)
        holder.bind(userWeight)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserWeight>(){

        override fun areItemsTheSame(oldItem: UserWeight, newItem: UserWeight): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserWeight, newItem: UserWeight): Boolean {
            return oldItem.id == newItem.id
        }
    }


}