package com.example.jiaozihang.coinz

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat.*
import android.util.Log
import android.widget.Toast
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import kotlinx.android.synthetic.main.activity_third.*
import com.mapbox.mapboxsdk.annotations.IconFactory
import java.time.LocalDate
import java.time.LocalDateTime

class ThirdActivity : AppCompatActivity(), PermissionsListener
        , LocationEngineListener, OnMapReadyCallback {

    private val tag = "ThirdActivity"
    private var mapView: MapView? = null
    private var map: MapboxMap? = null
    private lateinit var permissionManager: PermissionsManager
    private lateinit var originLocation: Location //store current location all time
    private lateinit var locationEngine: LocationEngine
    private lateinit var locationLayerPlugin: LocationLayerPlugin
    //work with locationEngine to give a UI display to user


    /** initialise the icon my markers, which represents different types of currency. */

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle: Bundle? = intent.extras



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        mapView = findViewById(R.id.mapboxMapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)


        var Month = LocalDateTime.now().monthValue.toString()
        if (Month.length == 1) {
            Month = "0" + Month
        }
        var Date = LocalDate.now().dayOfMonth.toString()
        if (Date.length == 1) {
            Date = "0" + Date
        }

        DownloadFileTask(DownloadCompleteRunner)
                .execute("http://homepages.inf.ed.ac.uk/stg/coinz/"
                        + LocalDate.now().year.toString()
                        + "/" + Month + "/" + Date + "/coinzmap.geojson")


        collect.setOnClickListener {

            CoinsObject.pickupcoins(originLocation)
            /** call the pickup CoinsObject function */

            map!!.clear()
            /** clear all markers on the map, inorder to map them again without mapping those
             * collected CoinsObject*/

            for (i in 0..(CoinsObject.the_coin.size - 1)) {
                val lati1 = CoinsObject.the_coin[i].latitude
                val long1 = CoinsObject.the_coin[i].longitude
                val cur1 = CoinsObject.the_coin[i].currency
                val value1 = CoinsObject.the_coin[i].the_value
                val iconfrog = IconFactory
                        .getInstance(this@ThirdActivity)
                        .fromBitmap(getBitmapFromVectorDrawable
                        (this@ThirdActivity, R.drawable.ic_frog))

                val iconlizard = IconFactory
                        .getInstance(this@ThirdActivity)
                        .fromBitmap(getBitmapFromVectorDrawable
                        (this@ThirdActivity, R.drawable.ic_lizard))

                val iconturtle = IconFactory
                        .getInstance(this@ThirdActivity)
                        .fromBitmap(getBitmapFromVectorDrawable
                        (this@ThirdActivity, R.drawable.ic_turtle))

                val iconcrocodile = IconFactory
                        .getInstance(this@ThirdActivity)
                        .fromBitmap(getBitmapFromVectorDrawable
                        (this@ThirdActivity, R.drawable.ic_crocodile_))

                if (cur1 == "\"SHIL\"") {
                    map!!.addMarker(MarkerOptions().title(cur1).snippet(value1)
                            .position(LatLng(lati1, long1)).icon(iconfrog))
                }
                if (cur1 == "\"DOLR\"") {
                    map!!.addMarker(MarkerOptions().title(cur1).snippet(value1)
                            .position(LatLng(lati1, long1)).icon(iconlizard))
                }
                if (cur1 == "\"QUID\"") {
                    map!!.addMarker(MarkerOptions().title(cur1).snippet(value1)
                            .position(LatLng(lati1, long1)).icon(iconturtle))
                }
                if (cur1 == "\"PENY\"") {
                    map!!.addMarker(MarkerOptions().title(cur1).snippet(value1)
                            .position(LatLng(lati1, long1)).icon(iconcrocodile))
                }
                /** check the currency of the coin, and set different icon to the marker. */
            }

            if (CoinsObject.wallet.size == 100) {
                Toast.makeText(this,
                        "your wallet is full, store some CoinsObject in to the bank!",
                        Toast.LENGTH_SHORT).show()
                /** remind user when their wallet is full and can not collect anymore */
            }
        }


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

        for (i in FeatureCollection.fromJson(DownloadCompleteRunner.result).features()!!) {
            val long = i.geometry().toString()
                    .substringAfter("[")
                    .substringBefore(",").toDouble()
            val lati = i.geometry().toString()
                    .substringAfter("[")
                    .substringAfter(",")
                    .substringBefore("]").toDouble()
            val cur = i.properties()!!["currency"].toString()
            val value = i.properties()!!["value"].toString()
            val ID = i.properties()!!["id"].toString()
            /** read the dowaloaded file and read the information stored in it. */


            var checker = 0

            for (i in CoinsObject.coin_collected_today) {
                if (i == ID) {
                    checker = 1
                    break
                }
            }

            if (checker == 1) {
                continue
            }
            /** set a checker, if the coin in the loop is same as the coin the user have collected,
             * skip the marker adding step and go to the next loop */

            val loc = Location("")
            loc.latitude = lati
            loc.longitude = long
            val iconfrog = IconFactory
                    .getInstance(this@ThirdActivity)
                    .fromBitmap(getBitmapFromVectorDrawable
                    (this@ThirdActivity, R.drawable.ic_frog))

            val iconlizard = IconFactory
                    .getInstance(this@ThirdActivity)
                    .fromBitmap(getBitmapFromVectorDrawable
                    (this@ThirdActivity, R.drawable.ic_lizard))

            val iconturtle = IconFactory
                    .getInstance(this@ThirdActivity)
                    .fromBitmap(getBitmapFromVectorDrawable
                    (this@ThirdActivity, R.drawable.ic_turtle))

            val iconcrocodile = IconFactory
                    .getInstance(this@ThirdActivity)
                    .fromBitmap(getBitmapFromVectorDrawable
                    (this@ThirdActivity, R.drawable.ic_crocodile_))

            CoinsObject.the_coin.add(Coin(cur, value, lati, long, ID))

            if (cur == "\"SHIL\"") {
                map!!.addMarker(MarkerOptions().title(cur).snippet(value)
                        .position(LatLng(lati, long)).icon(iconfrog))
            }
            if (cur == "\"DOLR\"") {
                map!!.addMarker(MarkerOptions().title(cur).snippet(value)
                        .position(LatLng(lati, long)).icon(iconlizard))
            }
            if (cur == "\"QUID\"") {
                map!!.addMarker(MarkerOptions().title(cur).snippet(value)
                        .position(LatLng(lati, long)).icon(iconturtle))
            }
            if (cur == "\"PENY\"") {
                map!!.addMarker(MarkerOptions().title(cur).snippet(value)
                        .position(LatLng(lati, long)).icon(iconcrocodile))
            }
            /** add this coin to a list and coin, and add a marker for it on the map */
        }

    }

    private fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d(tag, "Permissions are granted")
            initialiseLocationEngine()
            initialiseLocationLayer()
        } else {
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
        } else {
            if (map == null) {
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

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableId)!!
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (wrap(drawable)).mutate()
        }

        var bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)

        return bitmap
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