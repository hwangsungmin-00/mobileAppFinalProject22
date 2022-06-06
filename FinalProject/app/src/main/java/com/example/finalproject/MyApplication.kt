package com.example.finalproject

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.common.KakaoSdk
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    companion object{
        //데이터가져오기
        var networkService : NetworkService
        //val networkServiceXml : NetworkService
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        //val parser= TikXml.Builder().exceptionOnUnreadXml(false).build()
        //val retrofitXml : Retrofit
        //    get() = Retrofit.Builder()
        //        .baseUrl("http://apis.data.go.kr/")
        //        .addConverterFactory(TikXmlConverterFactory.create(parser))
        //        .build()

        //로그인
        lateinit var auth: FirebaseAuth
        var email:String?=null
        lateinit var db : FirebaseFirestore
        lateinit var storage: FirebaseStorage

        fun checkAuth() : Boolean{
            var currentUser = auth.currentUser
            return currentUser?.let{
                email=currentUser.email
                currentUser.isEmailVerified
            }?: let{
                false
            }
        }




        init{
            networkService = retrofit.create(NetworkService::class.java)
            //networkServiceXml = retrofitXml.create(NetworkService::class.java)
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        KakaoSdk.init(this, "9877438a9516dafd8a0556ac731ba010")

        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }




}
