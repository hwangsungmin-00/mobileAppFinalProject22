package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.databinding.ActivityDataBinding

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_data)
        val binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RetrofitFragment()
        //val xmlfragment = XmlFragment()
        val bundle = Bundle()
        binding.searchBtn.setOnClickListener{
            when(binding.rGroup.checkedRadioButtonId){
                R.id.json -> bundle.putString("returnType", "json")
                R.id.xml -> bundle.putString("returnType", "xml")
                else -> bundle.putString("returnType", "json")
            }
            if(binding.rGroup.checkedRadioButtonId==R.id.json){
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, fragment)
                    .commit()
            }
            /*
            else if(binding.rGroup.checkedRadioButtonId==R.id.xml){
                xmlfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }*/

        }
    }
}