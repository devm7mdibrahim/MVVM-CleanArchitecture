package com.aait.utils.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.lang.ref.WeakReference


class CurrentLocation constructor(
    private val appContext: Activity,
    private val needUpdates: Boolean = false
) : LiveData<Location?>() {

    private val mFusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    private var weakActivity: WeakReference<Activity>? = null

    private var mLocationRequest: LocationRequest? = null

    private var counter = 0

    private var mLocationCallback: LocationCallback? = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                counter++
                if (location != null) {
                    if (counter > 1 && !needUpdates) {
                        return
                    }
                    value = location
                }
            }
        }
    }

    init {
        startInit()
    }

    fun startInit() {
        weakActivity = WeakReference(appContext)

        Dexter.withContext(weakActivity!!.get())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        getLocation()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            })
            .onSameThread().check()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {

        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                value = location
                mFusedLocationClient.removeLocationUpdates(mLocationCallback!!)
            }
        }
        createLocationRequest()
        checkDeviceSettings()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if (value == null) mFusedLocationClient.requestLocationUpdates(
            mLocationRequest!!,
            mLocationCallback!!,
            Looper.getMainLooper()
        )
    }

    override fun onInactive() {
        super.onInactive()
        if (mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback!!)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var instance: CurrentLocation? = null
        fun getInstance(appContext: Activity): CurrentLocation {
            if (instance == null) {
                instance = CurrentLocation(appContext)
            } else {
                instance?.startInit()
            }
            return instance as CurrentLocation
        }
    }


    private fun checkDeviceSettings() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)
        builder.setAlwaysShow(true)
        val client: SettingsClient = LocationServices.getSettingsClient(appContext)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener {

            try {
                task.getResult(ApiException::class.java)
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        showDialog(exception)
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                    }
                    LocationSettingsStatusCodes.SUCCESS -> {
                        getLocation()
                    }

                }
            }
        }

    }

    private fun showDialog(exception: ApiException) {
        try {
            val resolvable = exception as ResolvableApiException
            resolvable.startResolutionForResult(appContext, 100)
        } catch (e: IntentSender.SendIntentException) {
            // Ignore the error.
        }
    }

}