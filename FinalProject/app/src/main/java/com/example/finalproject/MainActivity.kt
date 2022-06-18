package com.example.finalproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject.databinding.ActivityDataBinding
import com.example.finalproject.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var intent1 : Intent
    lateinit var binding : ActivityMainBinding

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferences2: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))
        sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this)
        val textShape = sharedPreferences2.getString("textshape", "")
        binding.openapicome.setTextColor(Color.parseColor(textShape))
        binding.qrbtn.setTextColor(Color.parseColor(textShape))
        binding.calculate.setTextColor(Color.parseColor(textShape))
        binding.diarybtn.setTextColor(Color.parseColor(textShape))
        binding.mapbtnmain.setTextColor(Color.parseColor(textShape))
        binding.youtubebtn.setTextColor(Color.parseColor(textShape))





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

        binding.mapbtnmain.setOnClickListener{
            val intent5 = Intent(this, MapActivity::class.java)
            startActivity(intent5)
        }
        binding.youtubebtn.setOnClickListener{
            val intent7 = Intent(this, YoutubeActivity::class.java)
            startActivity(intent7)
        }

        val requestGalleryLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            try{
                val calRatio=calculateInSampleSize(
                    it.data!!.data!!, 150, 150
                )
                val option = BitmapFactory.Options()
                //option.inSampleSize=4
                var inputStream=contentResolver.openInputStream(it.data!!.data!!)
                val bitmap= BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream=null

                bitmap?.let {
                    binding.userIdImg.setImageBitmap(bitmap)
                }?: let{
                    Log.d("mobileApp", "bitmap null")
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.galleryByn.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            requestGalleryLauncher.launch(intent)
        }
        val requestCameraThumnailLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val bitmap = it?.data?.extras?.get("data") as Bitmap
            binding.userIdImg.setImageBitmap(bitmap)
        }
        binding.cameraByn.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestCameraThumnailLauncher.launch(intent)
        }

        binding.viewpager.adapter=MyFragmentPagerAdapter(this)



    }

    override fun onResume() {
        super.onResume()
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))

        val textShape = sharedPreferences2.getString("textshape", "")
        binding.openapicome.setTextColor(Color.parseColor(textShape))
        binding.qrbtn.setTextColor(Color.parseColor(textShape))
        binding.calculate.setTextColor(Color.parseColor(textShape))
        binding.diarybtn.setTextColor(Color.parseColor(textShape))
        binding.mapbtnmain.setTextColor(Color.parseColor(textShape))
        binding.youtubebtn.setTextColor(Color.parseColor(textShape))



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

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity)
    {
        val fragments: List<Fragment>
        init{
            fragments=listOf(TwoFragment(), OneFragment())

        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}

