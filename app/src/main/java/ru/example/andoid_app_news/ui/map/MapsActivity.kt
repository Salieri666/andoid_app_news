package ru.example.andoid_app_news.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.ActivityMapsBinding
import ru.example.andoid_app_news.model.viewmodel.MapViewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        mapViewModel.watchRoute.observe(this) {
            if (!it) {
                binding.floatingMapBtn.setOnClickListener {
                    mapViewModel.cancelRoute()
                }
                binding.floatingMapBtn.setImageResource(R.drawable.ic_baseline_check_24)
            } else {
                binding.floatingMapBtn.setOnClickListener {
                    mapViewModel.startRoute()
                }
                binding.floatingMapBtn.setImageResource(R.drawable.ic_baseline_clear_24)
            }
        }

        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewModel.watchRoute.removeObservers(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            }
            else -> {
                // No location access granted.
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mapViewModel.watchRoute.observe(this) {
            if (!it) {
                val polyline = PolylineOptions()
                mMap.addPolyline(polyline)
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
        val locationManager : LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = locationManager.getBestProvider(criteria, true)



        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val location: Location? = locationManager.getLastKnownLocation(bestProvider!!)
        if (location != null) {
            onLocationChanged(location)
        }
        locationManager.requestLocationUpdates(bestProvider, 2000, 0f, this)
    }

    override fun onLocationChanged(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.clear()
        val marker = mMap.addMarker(MarkerOptions().position(latLng))
        marker!!.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

            if (mapViewModel.watchRoute.value!!) {
                mapViewModel.addPoints(latLng)
                val polyline = PolylineOptions()
                mapViewModel.points.value?.forEach {
                    polyline.add(it)
                }
                mMap.addPolyline(polyline)
            }

    }
}