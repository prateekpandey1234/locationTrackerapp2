package com.example.googlemapssdk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.annotations.NonNls

class MapActivity :AppCompatActivity(),OnMapReadyCallback {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapFragment:SupportMapFragment
    private lateinit var map1 : GoogleMap
    private var locationGranted:Boolean= false
    private var fine_location :String = Manifest.permission.ACCESS_FINE_LOCATION
    private var coarse_location :String = Manifest.permission.ACCESS_COARSE_LOCATION
    private var LOCATION_PERMISSION_REQUEST_CODE:Int = 1234


    @SuppressLint("VisibleForTests", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        requestPermissions()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
            mapView.getMapAsync {
                map1 = it
                map1.isMyLocationEnabled = true
                updatelocation()
            }
            mapView.onCreate(savedInstanceState)

    }

    private fun requestPermissions(){
        var array1 = arrayOf(fine_location,coarse_location)
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                fine_location) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    coarse_location) == PackageManager.PERMISSION_GRANTED){
                locationGranted = true
            }
            else{
                ActivityCompat.requestPermissions(this,
                    array1,
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        }else{
            ActivityCompat.requestPermissions(this,
                array1,
                LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationGranted = false
        if(grantResults.size>0){
            Log.d("jojo","popop")
            for(i in 0..grantResults.size ){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    locationGranted = false
                    Log.d("no bro", "onRequestPermissionsResult: permission failed")
                    return
                }
            }
        }
        Log.d("yes start","fuckyeah")
        locationGranted = true
    }
//Use the onMapReady callback method to get a handle to the GoogleMapobject.
// The callback is triggered when the map is ready to receive user input. It provides a
// non-null instance of the GoogleMap class that you can use to update the map.
    private fun moveCamer(latlng:LatLng ){
        map1.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,15f))
    }
    @SuppressLint("MissingPermission")
    private fun updatelocation(){
        Log.d("hmmm","yes update")
        val location = fusedLocationProviderClient.lastLocation
        location.addOnCompleteListener{Task1->
            if(Task1.isSuccessful){
                @NonNull
                var currLocation= Task1.result
                //cracking the address using latitude and longitude
                var loc = LatLng(currLocation.latitude!!,currLocation.longitude!!)
                Log.d("location","$loc")
                var geocoder = Geocoder(this)
                var address = geocoder.getFromLocation(currLocation.latitude,currLocation.longitude,1)
                Log.d("location","$address")
                moveCamer(loc)
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        map1 = p0

    }
    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }
    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }
    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}