package com.example.finalproject

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.ActivityMapBinding
import com.example.finalproject.databinding.DialogMapBinding
import com.example.finalproject.databinding.FragmentRetrofitBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    var googleMap: GoogleMap? = null
    var lats = ArrayList<String>()
    var lngs = ArrayList<String>()
    var storename = ArrayList<String>()
    lateinit var apiClient: GoogleApiClient
    lateinit var providerClient: FusedLocationProviderClient
    var a: Int = 0

    var latitude =10.0
    var longitude =10.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        val call: Call<PageListModel> = MyApplication.networkService.getList(
            "UZqOXy4njGcLlzRF3dc1CkzTh/6RreiyUPk+iRLTrHRIqhnXaXKcEP2PJcSRFt8/2C9oT3uBJhoVrGiCL97Img==",
            1,
            200,
            "JSON"
        )

        call?.enqueue(object : Callback<PageListModel> {
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {


                if (response.isSuccessful) {
                    Log.d("mobileApp", "$response")
                    Log.d("mobileApp", "${response.body()}")
                    //binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(this)
                    //binding.retrofitRecyclerView.adapter = MyAdapter(activity as Context, response.body()?.response!!.body!!.items.item)

                    //var wtm:MutableList<ItemModel>?
                    var wtm = response.body()?.response!!.body!!.items.item
                    var wtm2: ItemModel?
                    for (i in 0 until wtm!!.size) {
                        wtm2 = wtm?.get(i)

                        lats.add(wtm2!!.lat)
                        lngs.add(wtm2!!.lot)
                        storename.add(wtm2!!.bsn_nm)
                        a=a+1

                    }

                    moveMap(latitude, longitude)
                    Log.d("mobileApp", "${wtm?.get(0)?.lot} 이고")
                    Log.d("mobileApp", "${wtm?.get(1)?.lot} 이다")
                    Log.d("mobileApp", "${wtm?.get(5)?.bsn_nm} 이다")
                    Log.d("mobileApp", "${a}")

                }
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                Log.d("mobileApp", "$t 체크")
            }
        })



        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value == true }) {
                apiClient.connect()
            } else {
                Toast.makeText(this, "권한 거부..", Toast.LENGTH_SHORT).show()
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE
                )
            )
        } else {
            apiClient.connect()
        }


        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(
            this
        )

    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        val dialogBinding = DialogMapBinding.inflate(layoutInflater)

        googleMap!!.setOnMarkerClickListener(object:GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker): Boolean {

                val eventHandler = object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        if(p1==DialogInterface.BUTTON_POSITIVE){
                            marker!!.title = dialogBinding.mapTitle.text.toString()

                        }
                    }
                }
                AlertDialog.Builder(this@MapActivity).run{
                    setTitle("마커 이름")
                    if(dialogBinding.root.parent != null)
                        (dialogBinding.root.parent as ViewGroup).removeView(dialogBinding.root)
                    setView(dialogBinding.root)
                    setPositiveButton("확인", eventHandler)
                    setNegativeButton("취소", null)
                    show()
               }
                return false
            }
        })
        googleMap!!.setOnMapClickListener(object: GoogleMap.OnMapClickListener{
            override fun onMapClick(latltn: LatLng) {
                val eventHandler = object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        if(p1==DialogInterface.BUTTON_POSITIVE){
                            val markerOptions = MarkerOptions()
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            markerOptions.position(latltn!!)
                            markerOptions.title(dialogBinding.mapTitle.text.toString())

                            googleMap?.addMarker(markerOptions)
                        }
                    }
                }
                AlertDialog.Builder(this@MapActivity).run{
                    setTitle("마커 이름")
                    if(dialogBinding.root.parent != null)
                        (dialogBinding.root.parent as ViewGroup).removeView(dialogBinding.root)
                    setView(dialogBinding.root)
                    setPositiveButton("확인", eventHandler)
                    setNegativeButton("취소", null)
                    show()
                }
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 1, 0, "위성지도")
        menu?.add(0, 2, 0, "일반지도")
        return true
    }


    private fun moveMap(latitide: Double, longitude: Double) {
        var latLng2 = LatLng(37.46839171, 126.6470054)
        Log.d("mobileApp", "${a}")
        for (i in 0 until a) {
            //Log.d("mobileApp", "룰라")
            latLng2 = LatLng(lats[i].toDouble(), lngs[i].toDouble())
            googleMap!!.addMarker(MarkerOptions().position(latLng2).title(storename[i]))
            Log.d("mobileApp", "${lats[i]} 이고")
            Log.d("mobileApp", "${lngs[i]} 이고다")
            //markerOp = MarkerOptions()
            //markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            //markerOp.position(latLng)
            //markerOp.title(storename[i])
            //googleMap?.addMarker(markerOp)

        }
        val latLng = LatLng(latitide, longitude)

        Log.d("mobileApp", "${latitide}지금")
        Log.d("mobileApp", "${longitude}지금")
        val position: CameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("My Location")
        googleMap?.addMarker(markerOp)
    }

    override fun onConnected(p0: Bundle?) {
        //TODO("Not yet implemented")
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) === PackageManager.PERMISSION_GRANTED
        ) {
            providerClient.lastLocation.addOnSuccessListener(
                this@MapActivity,
                object : OnSuccessListener<Location> {
                    override fun onSuccess(p0: Location?) {
                        p0?.let {
                            latitude = p0.latitude
                            longitude = p0.longitude
                            Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            //moveMap(latitude, longitude)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        //TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        //TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            2 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }
        return false
    }
}