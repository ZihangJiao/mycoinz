package com.example.jiaozihang.coinz

import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.Mapbox.*
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.android.synthetic.main.activity_third.view.*
import java.net.URL



class ThirdActivity : AppCompatActivity(), PermissionsListener, LocationEngineListener, OnMapReadyCallback {

    private val tag = "ThirdActivity"
    private var mapView: MapView? = null
    private var map: MapboxMap? = null
    private lateinit var permissionManager: PermissionsManager
    private lateinit var originLocation: Location //store current location all time
    private lateinit var locationEngine: LocationEngine
    private lateinit var locationLayerPlugin: LocationLayerPlugin//work with locationEngine to give a UI display to user

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        val msg = bundle!!.getString("user_message")
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        mapView = findViewById(R.id.mapboxMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        DownloadFileTask(DownloadCompleteRunner).execute("http://homepages.inf.ed.ac.uk/stg/coinz/2018/10/03/coinzmap.geojson")




    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Log.d(tag, "Permissions: $permissionsToExplain")
    }

    override fun onPermissionResult(granted: Boolean) {
        Log.d(tag, "[onPermissionResult] granted == $granted")
        if (granted) {
            enableLocation()
        } else {

        }
    }

    override fun onLocationChanged(location: Location?) {
        if (location == null) {
            Log.d(tag, "[onLocationChanged] location is null")
        } else {
            originLocation = location
            setCameraPosition(originLocation)
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onConnected() {
        Log.d(tag, "[onConnected] requesting location updates")
        locationEngine.requestLocationUpdates()
    }

    override fun onMapReady(mapboxMap: MapboxMap?) {
        if (mapboxMap == null) {
            Log.d(tag, "[onMapReady] mapboxMap is null")
        } else {
            map = mapboxMap
            map?.uiSettings?.isCompassEnabled = true
            map?.uiSettings?.isZoomControlsEnabled = true
            enableLocation()
        }

        print ((DownloadCompleteRunner.result))

        for ( i in  FeatureCollection.fromJson(DownloadCompleteRunner.result).features()!!){
            val p2 = i.geometry().toString().substringAfter("[").substringBefore(",").toDouble()
            val p1 = i.geometry().toString().substringAfter("[").substringAfter(",").substringBefore("]").toDouble()
            val cur = i.properties()!!["currency"].toString()
            val value = i.properties()!!["value"].toString()
            map!!.addMarker(MarkerOptions().title(cur).snippet(value).position(LatLng(p1,p2)))
        }

    }

    private fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d(tag, "Permissions are granted")
            initialiseLocationEngine()
            initialiseLocationLayer()
        } else {
            Log.d(tag, "Permissions are not granted")
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun initialiseLocationEngine() {
        locationEngine = LocationEngineProvider(this).obtainBestLocationEngineAvailable()
        locationEngine.apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationEnginePriority.HIGH_ACCURACY
            activate()
        }
        val lastLocation = locationEngine.lastLocation
        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine.addLocationEngineListener(this)
        }

    }


    @SuppressWarnings("MissingPermission")
    private fun initialiseLocationLayer() {
        if (mapView == null) {
            Log.d(tag, "mapView is null")
        } else {
            if (map == null) {
                Log.d(tag, "map is null")
            } else {
                locationLayerPlugin = LocationLayerPlugin(mapView!!, map!!, locationEngine)
                locationLayerPlugin.apply {
                    setLocationLayerEnabled(true)
                    cameraMode = CameraMode.TRACKING
                    renderMode = RenderMode.NORMAL
                }
            }
        }
    }

    private fun setCameraPosition(location: Location) {
        val latlng = LatLng(location.latitude, location.longitude)
        map?.animateCamera(CameraUpdateFactory.newLatLng(latlng))
    }

    public override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }


}