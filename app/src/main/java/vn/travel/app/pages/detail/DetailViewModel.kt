package vn.travel.app.pages.detail

import androidx.lifecycle.MutableLiveData
import vn.travel.app.base.BaseViewModel
import vn.travel.domain.model.CategoryModel
import vn.travel.domain.model.ImageModel
import vn.travel.domain.model.ServiceModel

class DetailViewModel : BaseViewModel() {
	
	val category = MutableLiveData<List<CategoryModel>>(listOf())
	val service = MutableLiveData<List<ServiceModel>>(listOf())
	val images = MutableLiveData<List<ImageModel>>(listOf())
}