package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemMainlistBinding

class MyViewHolder(val binding: ItemMainlistBinding): RecyclerView.ViewHolder(binding.root)
class MyAdapter (val context: Context, val datas:MutableList<ItemModel>?):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas?.size ?:0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainlistBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val model = datas!![position]
        binding.name.text = model.bsn_nm
        binding.dong.text = model.jdx_dong
        binding.ryu.text = model.businesscondition
        binding.addr.text = model.road_nm_addr
        binding.lat.text = model.lat
        binding.lot.text = model.lot
    }
}