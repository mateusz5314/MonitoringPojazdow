package com.example.lokalizator

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.example.lokalizator.gpsAccess.Finder
import com.example.lokalizator.networkApi.Client
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var latitudeValText: TextView? = null
    private var longitudeValText: TextView? = null
    private var driverIdText: EditText? = null
    private var vehicleIdText: EditText? = null
    private var driverIdVal: Int = -1
    private var vehicleIdVal: Int = -1
    private var finder: Finder? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        latitudeValText = findViewById(R.id.latitudeVal)
        longitudeValText = findViewById(R.id.longitudeVal)
        driverIdText = findViewById(R.id.driverIdValue)
        vehicleIdText = findViewById(R.id.vehicleIdValue)

        val actualLocationButton: Button = findViewById(R.id.getLocation);
        actualLocationButton.setOnClickListener {
            onGetLocationButtonEvent()
        }
        val updateIdsButton: Button = findViewById(R.id.updateIds);
        updateIdsButton.setOnClickListener {
            onUpdateIdsButtonEvent()
        }
        finder = Finder()
        finder!!.init(this, this)

        mainHandler.post(object : Runnable {
            override fun run() {
                sendLocation()
                mainHandler.postDelayed(this, 5000)
            }
        })
    }

    private fun sendLocation()
    {
        val jsonObject = JSONObject()
        jsonObject.put("driverId", driverIdVal)
        jsonObject.put("vehicleId", vehicleIdVal)
        jsonObject.put("timestamp", System.currentTimeMillis())
        jsonObject.put("latitude", finder!!.latitude)
        jsonObject.put("longitude", finder!!.longitude)

        if (driverIdVal > 0 && vehicleIdVal > 0){
            if (finder!!.latitude != 0.0 || finder!!.longitude != 0.0) {
                val body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
                Log.e("JSON_OBJECT", jsonObject.toString())
                Client().getClient().sendLocation(body).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {}

                    override fun onFailure(call: Call<String>?, t: Throwable) {
                        t.message?.let { Log.e("TAG", it) }
                    }
                })
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            finder?.fineLocationRequestCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION", "Permission has been denied by user")
                } else {
                    Log.i("PERMISSION", "Permission has been granted by user")
                    finder!!.startLocationUpdates()
                }
            }
            finder?.coarseLocationRequestCode -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION", "Permission has been denied by user")
                } else {
                    Log.i("PERMISSION", "Permission has been granted by user")
                    finder!!.startLocationUpdates()
                }
            }
        }
    }

    private fun onGetLocationButtonEvent() {
        latitudeValText!!.text = finder!!.latitude.toString()
        longitudeValText!!.text = finder!!.longitude.toString()
    }

    private fun onUpdateIdsButtonEvent() {
        try {
            driverIdVal = driverIdText?.text.toString().toInt()
            vehicleIdVal = vehicleIdText?.text.toString().toInt()
        }
        catch (e : NumberFormatException)
        {
            Log.e("UPDATE_IDS", e.toString())
            driverIdVal = -1;
            vehicleIdVal = -1;
        }
    }
}