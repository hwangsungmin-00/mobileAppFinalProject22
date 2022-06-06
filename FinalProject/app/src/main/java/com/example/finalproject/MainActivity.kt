package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.finalproject.databinding.ActivityDataBinding
import com.example.finalproject.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var intent1 : Intent
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val keyHash = Utility.getKeyHash(this)
        //Log.d("mobileApp", keyHash)

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, AuthActivity::class.java)
            if(binding.btnLogin.text.equals("로그인"))
                intent.putExtra("data", "logout")
            else if(binding.btnLogin.text.equals("로그아웃"))
                intent.putExtra("data", "login")
            startActivity(intent)
        }

        binding.openapicome.setOnClickListener{
            intent1 = Intent(this, DataActivity::class.java)
            startActivity(intent1)
        }

        binding.calculate.setOnClickListener{
            val intent2 = Intent(this, CalculateActivity::class.java)
            startActivity(intent2)
        }
        binding.settingbtn.setOnClickListener{
            val intent3 = Intent(this, SettingActivity::class.java)
            startActivity(intent3)
        }
        binding.diarybtn.setOnClickListener{
            val intent4 = Intent(this, DiaryActivity::class.java)
            startActivity(intent4)
        }

        binding.qrbtn.setOnClickListener{
            val intent5 = Intent(this, CaptureActivity::class.java)
            startActivity(intent5)
        }


    }

    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth() || MyApplication.email!=null){
            binding.btnLogin.text="로그아웃"
            binding.authTv.text = "${MyApplication.email}님 반갑습니다."
            binding.authTv.textSize=16F
            //binding.mainRecyclerView.visibility = View.VISIBLE
            //makeRecyclerView()
        }
        else{
            binding.btnLogin.text="로그인"
            binding.authTv.text="덕성 모바일"
            binding.authTv.textSize=24F
            //binding.mainRecyclerView.visibility = View.GONE
        }
    }
}