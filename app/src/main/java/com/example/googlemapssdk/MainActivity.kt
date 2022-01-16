package com.example.googlemapssdk

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_main.*
//add following dependencies
//implementation 'com.google.android.gms:play-services-location:19.0.1'
//    implementation 'com.google.android.gms:play-services-maps:18.0.2'
//
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
//
//    // KTX for the Maps SDK for Android
//    implementation 'com.google.maps.android:maps-ktx:3.2.1'
//
//    // (Optional) KTX for the Maps SDK for Android Utility Library
//    implementation 'com.google.maps.android:maps-utils-ktx:3.2.1'

//ADD THE FOLLOWING PERMISSIONS IN MANIFEST
//<uses-permission android:name="android.permission.INTERNET"/>
//    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>
//    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
//    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
//    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>


//ADD the following meta data in application
//<meta-data
//            android:name="com.google.android.geo.API_KEY"
//            android:value="@string/API_KEY" />
//        <meta-data
//            android:name="com.google.android.gms.version"
//            android:value="@integer/google_play_services_version" />
class MainActivity : AppCompatActivity() {
    var available :Int= 0
    lateinit var dialog : Dialog
    var ERROR_DIALOG_REQUEST : Int = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        Log.d("nigga","$available")
        if(isServiceOK()){
            initialise()
        }
    }
    private fun initialise() {
        button.setOnClickListener{
            var intent  = Intent(this@MainActivity,MapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isServiceOK():Boolean{
        if(available==ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d("API DONE","$available")
            return true

        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occurred but we can resolve it
            Log.d("ERROR BUT CAN REMOVED","$available")
            dialog = GoogleApiAvailability.getInstance().getErrorDialog( this, available, ERROR_DIALOG_REQUEST)!!
            dialog.show()
            return true
        }
        else{
            Log.d("NOTHING CAN BE DONE","$available")
            return false
        }

    }
}