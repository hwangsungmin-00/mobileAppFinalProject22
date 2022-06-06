package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.ActivityDiaryBinding
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.util.myCheckPermission

class DiaryActivity : AppCompatActivity() {
    lateinit var binding : ActivityDiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_diary)

        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myCheckPermission(this)

        binding.addFab.setOnClickListener{
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, AddDiaryActivity::class.java))
            }
            else{
                Toast.makeText(this, "인증진행해주세요..", Toast.LENGTH_SHORT).show()
            }
        }
        if(MyApplication.checkAuth() || MyApplication.email!=null) {
            makeRecyclerView()
        }
    }

    private fun makeRecyclerView(){
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<DiaryItemData>()
                for(document in result){
                    val item = document.toObject(DiaryItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = DiaryAdapter(this, itemList)
            }
            .addOnFailureListener{
                Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_SHORT).show()
            }
    }
}