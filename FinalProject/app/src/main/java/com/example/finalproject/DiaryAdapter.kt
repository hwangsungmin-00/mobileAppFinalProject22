package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.DiaryItemMainBinding

class MyViewHolderd(val binding: DiaryItemMainBinding) : RecyclerView.ViewHolder(binding.root)
class DiaryAdapter(val context: Context, val itemList: MutableList<DiaryItemData>): RecyclerView.Adapter<MyViewHolderd>() {
    override fun getItemCount(): Int {
        return itemList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderd {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolderd(DiaryItemMainBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: MyViewHolderd, position: Int) {
        val data = itemList.get(position)
        holder.binding.run {
            itemEmailView.text = data.email
            itemDateView.text = data.date
            itemContentView.text = data.content
        }

        val imageRef = MyApplication.storage.reference.child("images/${data.docId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.itemImageView)
            }
        }
    }
}