package com.example.latestearthquakes.main.earthquakes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.latestearthquakes.R
import com.example.latestearthquakes.data.model.Depremler
import com.example.latestearthquakes.databinding.FragmentDetayliBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EarthquakesDetailedFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentDetayliBinding
    private lateinit var depremlerObject: Depremler
    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detayli, container, false)
        binding.map.onCreate(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        depremlerObject = arguments?.getSerializable("deprem_data") as Depremler
        binding.earthquakeData = depremlerObject
        binding.map.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val location = LatLng(depremlerObject.enlem, depremlerObject.boylam)
        mMap.addMarker((MarkerOptions().position(location).title(depremlerObject.yer)))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10.8f))
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onLowMemory()
    }

}