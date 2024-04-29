package vn.travel.app.pages.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import vn.travel.app.base.BaseViewModel

class MapViewModel : BaseViewModel() {
	val position = MutableLiveData<Pair<LatLng, String>?>(null)
}