package vn.travel.app.pages.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.travel.app.R
import vn.travel.app.base.BaseFragment
import vn.travel.app.databinding.FragmentMapBinding
import vn.travel.app.pages.main.RootViewModel

class MapFragment : BaseFragment<RootViewModel, MapViewModel, FragmentMapBinding>(),
	OnMapReadyCallback {
	
	override val sharedViewModel: RootViewModel by activityViewModel()
	override val viewModel: MapViewModel by viewModel()
	
	private lateinit var mMap: GoogleMap
	
	override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMapBinding =
		FragmentMapBinding::inflate
	
	override fun onInit(view: View, savedInstanceState: Bundle?) {
		viewBinding.layoutHeader.header.apply {
			title = getString(R.string.map)
			menu.clear()
			setNavigationOnClickListener { navController.popBackStack() }
		}
		// Loading Map View
		viewModel.setLoadingOverlay(true)
		viewBinding.vMap.onCreate(savedInstanceState)
		viewBinding.vMap.getMapAsync(this)
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
	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		viewModel.setLoadingOverlay(false)
		sharedViewModel.detail.value?.let {
			viewModel.position.value = Pair(LatLng(it.nLat, it.eLong), it.name)
		}
	}
	
	override fun bindViewModel() {
		super.bindViewModel()
		viewModel.position.observe(this) {
			it?.let {
				mMap.addMarker(MarkerOptions().position(it.first).title(it.second))
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.first, 15f))
			}
		}
	}
}