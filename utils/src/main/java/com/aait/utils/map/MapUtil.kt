package com.aait.utils.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import androidx.core.content.ContextCompat
import com.aait.utils.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object MapUtil {

    val ZOOM = 15f

    private val icon1 = "http://thabit.4hoste.com/marker1.png"
    val icon2 = "http://thabit.4hoste.com/marker2.png"
    val icon3 = "http://thabit.4hoste.com/marker3.png"

    // Add Marker To Map
    fun addMarker(
        context: Context,
        map: GoogleMap,
        location: LatLng,
    ): MarkerOptions {
        // Creating a marker
        val markerOptions = MarkerOptions()

        // Setting the position for the marker
        markerOptions.position(location)

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(getAddressLine(context, location))
            .snippet(getAddressLine(context, location))

        // Animating to the touched position
        val cameraPosition = CameraPosition.Builder().target(location).zoom(ZOOM).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Placing a marker on the touched position
        map.addMarker(markerOptions)
        return markerOptions
    }

    fun addMarker(
        context: Context,
        map: GoogleMap,
        location: LatLng,
        zoom: Float,
        clear: Boolean,
        icon: Int
    ): MarkerOptions {

        // Creating a marker
        val markerOptions = MarkerOptions()

        // Setting the position for the marker
        markerOptions.position(location)

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(getAddressLine(context, location))
            .snippet(getAddressLine(context, location))
        if (icon != 0) {
            markerOptions.icon(getBitmapDescriptor(context, icon))
        }
        // Clears the previously touched position
        if (clear) {
            map.clear()
        }

        // Animating to the touched position
        val cameraPosition = CameraPosition.Builder().target(location).zoom(zoom).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Placing a marker on the touched position
        map.addMarker(markerOptions)
        return markerOptions
    }

    fun addMarker(
        context: Context,
        map: GoogleMap,
        location: LatLng,
        zoom: Float,
        clear: Boolean,
        icon: Int,
        title: String,
        snippet: String,
    ): MarkerOptions {

        // Creating a marker
        val markerOptions = MarkerOptions()

        // Setting the position for the marker
        markerOptions.position(location)

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(title)
            .snippet(snippet)
        if (icon != 0) {
            markerOptions.icon(getBitmapDescriptor(context, icon))
        }
        // Clears the previously touched position
        if (clear) {
            map.clear()
        }

        // Animating to the touched position
        val cameraPosition = CameraPosition.Builder().target(location).zoom(zoom).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Placing a marker on the touched position
        map.addMarker(markerOptions)
        return markerOptions
    }

    fun addMarker(
        context: Context,
        map: GoogleMap,
        location: LatLng,
        zoom: Float,
        icon: Int,
    ): MarkerOptions {

        // Creating a marker
        val markerOptions = MarkerOptions()

        // Setting the position for the marker
        markerOptions.position(location)
        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(getAddressLine(context, location))
            .snippet(getAddressLine(context, location))
        if (icon != 0) {
            markerOptions.icon(getBitmapDescriptor(context, icon))
        }

        // Animating to the touched position
        val cameraPosition = CameraPosition.Builder().target(location).zoom(zoom).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Placing a marker on the touched position
        map.addMarker(markerOptions)

        return markerOptions
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getBitmapDescriptor(context: Context, id: Int): BitmapDescriptor? {
        val vectorDrawable = context.getDrawable(id)
        val h = context.resources.getDimension(com.intuit.sdp.R.dimen._35sdp).toInt()
        val w = context.resources.getDimension(com.intuit.sdp.R.dimen._35sdp).toInt()

        vectorDrawable?.setBounds(0, 0, w, h)
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }

    // get Address Info
    fun getGeoCoderAddress(context: Context?, location: LatLng?): List<Address>? {
        if (location != null) {
            val geocoder = Geocoder(context, Locale.forLanguageTag("ar"))
            try {
                /*
             * Geocoder.getFromLocation - Returns an array of Addresses
             * that are known to describe the area immediately surrounding the given latitude and longitude.
             */
                return geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
            } catch (e: IOException) {
                //e.printStackTrace();
            }
        }
        return null
    }

    fun getLocality(context: Context, location: LatLng): String? {
        val addresses = getGeoCoderAddress(context, location)

        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            address.locality
        } else {
            null
        }
    }

    fun getAddressLine(context: Context, location: LatLng): String? {
        val addresses = getGeoCoderAddress(context, location)

        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            address.getAddressLine(0)
        } else {
            null
        }
    }

    fun getCountry(context: Context, location: LatLng): String? {
        val addresses = getGeoCoderAddress(context, location)

        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            address.countryName
        } else {
            null
        }
    }

    fun getAddress(context: Context, location: LatLng): Address? {
        val addresses = getGeoCoderAddress(context, location)

        return if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            address
        } else {
            null
        }
    }

    // Location and map Setting
    fun converter(lat: Double, lng: Double): LatLng {
        return LatLng(lat, lng)
    }

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (sin(deg2rad(lat1))
                * sin(deg2rad(lat2))
                + (cos(deg2rad(lat1))
                * cos(deg2rad(lat2))
                * cos(deg2rad(theta))))
        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }


    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun moveToLocation(
        lat: Double, lng: Double,
        map: GoogleMap, zoom: Float
    ) {
        // Animating to the touched position
        val cameraPosition =
            CameraPosition.Builder().target(converter(lat, lng)).zoom(zoom).build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun mapBoundsZoom(map: GoogleMap, locations: MutableList<LatLng>) {
        val builder = LatLngBounds.Builder()
        for (marker in locations) {
            builder.include(marker)
        }
        val bounds = builder.build()
        val padding = 0 // offset from edges of the map in pixels

        val boundsZoom = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        map.animateCamera(boundsZoom)
    }

    // Static Static Map Image
    fun createStaticMapImage(lat: Double, lng: Double, key: String): String {
        return "https://maps.google.com/maps/api/staticmap?center=$lat,$lng&zoom=18&size=640x480&scale=2&key=$key&markers=color:red%7Clabel:C%7C$lat,$lng"
    }

    // Create Static Map Image with Multi Location
    fun createStaticMapImage(locations: MutableList<LatLng>, key: String): String {
        var url =
            "https://maps.google.com/maps/api/staticmap?size=640x480&scale=2&key=$key&style=visibility:on"

        val icon = icon1
        locations.forEach {
            url += "&markers=icon:$icon%7Clabel:C%7C${it.latitude},${it.longitude}"
        }

        return url
    }

//    fun onDrawDirectionInStaticMapImage(
//        context: Context,
//        imageView: ImageView,
//        locations: MutableList<LatLng>,
//        key: String
//    ) {
//        if (locations.size == 1) {
//            HelperMethod.onLoadImageFromUrl(
//                imageView,
//                createStaticMapImage(locations, key),
//                context, R.drawable.ic_map
//            )
//        } else {
//            val request = object : StringRequest(
//                Method.GET, makeURL(
//                    locations, key
//                ), Response.Listener { response ->
//                    var createStaticMapImage = createStaticMapImage(locations, key)
//                    val json = JSONObject(response)
//                    val routeArray = json.getJSONArray("routes")
//                    val routes = routeArray.getJSONObject(0)
//                    val overviewPolylines = routes.getJSONObject("overview_polyline")
//                    val encodedString = overviewPolylines.getString("points")
//                    createStaticMapImage += "&path=color:0xfea211ff%7Cweight:5%7Cenc:$encodedString"
//                    HelperMethod.onLoadImageFromUrl(
//                        imageView,
//                        createStaticMapImage,
//                        context, R.drawable.ic_map
//                    )
//                }, Response.ErrorListener { }) {
//                override fun getParams(): Map<String, String> {
//                    return HashMap()
//                }
//            }
//            request.retryPolicy = DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//            Volley.newRequestQueue(context).add(request)
//        }
//    }

//    fun onDrawDirectionInStaticMapImage(
//        context: Context,
//        imageView: ImageView,
//        locations: MutableList<LatLng>,
//        key: String,
//        icon1: String,
//        icon2: String
//    ) {
//        if (locations.size == 1) {
//            HelperMethod.onLoadImageFromUrl(
//                imageView,
//                createStaticMapImage(locations, key, icon1, icon2),
//                context, R.drawable.ic_map
//            )
//        } else {
//            val request = object : StringRequest(
//                Method.GET, makeURL(
//                    locations, key
//                ), Response.Listener { response ->
//                    var createStaticMapImage =
//                        createStaticMapImage(locations, key, icon1, icon2)
//                    val json = JSONObject(response)
//                    val routeArray = json.getJSONArray("routes")
//                    val routes = routeArray.getJSONObject(0)
//                    val overviewPolylines = routes.getJSONObject("overview_polyline")
//                    val encodedString = overviewPolylines.getString("points")
//                    createStaticMapImage += "&path=color:0xfea211ff%7Cweight:5%7Cenc:$encodedString"
//                    HelperMethod.onLoadImageFromUrl(
//                        imageView,
//                        createStaticMapImage,
//                        context, R.drawable.ic_map
//                    )
//                }, Response.ErrorListener { }) {
//                override fun getParams(): Map<String, String> {
//                    return HashMap()
//                }
//            }
//            request.retryPolicy = DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//            Volley.newRequestQueue(context).add(request)
//        }
//    }

    fun makeURL(locations: MutableList<LatLng>, key: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${locations[0].latitude},${locations[0].longitude}&destination=${locations[1].latitude},${locations[1].longitude}" +
                "&sensor=false&mode=driving&alternatives=true&key=$key"
    }

    fun createStaticMapImage(
        locations: MutableList<LatLng>,
        key: String,
        icon1: String,
        icon2: String
    ): String {
        var url =
            "https://maps.google.com/maps/api/staticmap?size=640x480&scale=2&key=$key&style=visibility:on"

        var icon = icon1
        locations.forEachIndexed { index, it ->
            if (index == 1) {
                icon = icon2
            }
            url += "&markers=icon:$icon%7Clabel:C%7C${it.latitude},${it.longitude}"
        }

        return url
    }

    fun drawPath(result: String, context: Context, googleMap: GoogleMap) {
        try {
            val json = JSONObject(result)
            val routeArray = json.getJSONArray("routes")
            val routes = routeArray.getJSONObject(0)
            val overviewPolylines = routes.getJSONObject("overview_polyline")
            val encodedString = overviewPolylines.getString("points")

            val list = decodePoly(encodedString)

            for (z in 0 until list.size - 1) {
                val src = list[z]
                val dest = list[z + 1]
                googleMap.addPolyline(
                    PolylineOptions().add(
                        LatLng(src.latitude, src.longitude),
                        LatLng(dest.latitude, dest.longitude)
                    ).width(12f).color(
                        ContextCompat.getColor(context, R.color.colorPrimary)
                    ).geodesic(true)
                )
            }
        } catch (e: JSONException) {

        }
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

}