package com.example.lokalizator.gpsAccess

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lokalizator.MainActivity
import com.google.android.gms.location.*

class Finder {

    private var mLocationService: FusedLocationProviderClient? = null
    private lateinit var mLocationCallback: LocationCallback
    private var mIsInitDone: Boolean = false
    private var mContext: Context? = null
    private var mMainActivity: MainActivity? = null

    var fineLocationRequestCode = 100
        private set
    var coarseLocationRequestCode = 101
        private set
    var latitude: Double = 0.0
        private set
    var longitude: Double = 0.0
        private set

    fun init(mainActivity: MainActivity, context : Context){
        mLocationService = LocationServices.getFusedLocationProviderClient(mainActivity)
        mContext = context
        mMainActivity = mainActivity
        mIsInitDone = true
        startLocationUpdates()
    }

    fun startLocationUpdates() {
        if (!mIsInitDone){
            Log.d("ERROR", "Init is not done!")
            return
        }
        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    latitude = location!!.latitude
                    longitude = location.longitude
                    Log.d("POSITION", "latitude: $latitude")
                    Log.d("POSITION", "longitude: $longitude")
                }
            }
        }
        if (mContext?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED &&
            mContext?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED) {
            setupPermissions(Manifest.permission.ACCESS_FINE_LOCATION, fineLocationRequestCode)
            setupPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, coarseLocationRequestCode)
            return
        }
        mLocationService?.requestLocationUpdates(locationRequest,
            mLocationCallback,
            Looper.getMainLooper())
    }

    private fun setupPermissions(permissionName : String, reqCode : Int) {
        val permission = mContext?.let {
            ContextCompat.checkSelfPermission(
                it,
                permissionName)
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION", "Permission to record denied")
            makeRequest(permissionName, reqCode)
        }
    }

    private fun makeRequest(permissionName : String, reqCode : Int) {
        mMainActivity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(permissionName),
                reqCode)
        }
    }
}