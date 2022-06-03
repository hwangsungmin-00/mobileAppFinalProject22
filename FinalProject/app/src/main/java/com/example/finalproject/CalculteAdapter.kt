package com.example.finalproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.CalculateitemBinding

class MyViewHolderC(val binding: CalculateitemBinding): RecyclerView.ViewHolder(binding.root)

class CalculteAdapter(val datas: MutableList<CalculateItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolderC(CalculateitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolderC).binding
        binding.calculatework.text = datas!![position].calculatework
        binding.calculaterole.text = datas!![position].calculaterole
        //binding.todosetting.text = datas!![position].todosetting
        binding.calculateref.text = datas!![position].calculateref
    }
}