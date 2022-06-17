package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.ActivityYoutubeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeActivity : AppCompatActivity() {
    lateinit var binding : ActivityYoutubeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_youtube)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.schbtn.setOnClickListener{
            var call: Call<SearchListResponse> = MyApplication.networkService2.getList(
                "AIzaSyCEHGsUF7WlHwMHunk8pQfCOCunU_zdkPE",
                binding.input1.text.toString(),
                "video",
                "snippet"
            )

            call?.enqueue(object : Callback<SearchListResponse> {
                override fun onResponse(
                    call: Call<SearchListResponse>,
                    response: Response<SearchListResponse>
                ) {
                    if(response.isSuccessful){
                        binding.recyclerViewYoutube.layoutManager = LinearLayoutManager(this@YoutubeActivity)
                        binding.recyclerViewYoutube.adapter = YoutubeAdapter(this@YoutubeActivity, response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchListResponse>, t: Throwable) {
                    Log.d("mobileApp", "error......")
                }
            })
        }
    }
}