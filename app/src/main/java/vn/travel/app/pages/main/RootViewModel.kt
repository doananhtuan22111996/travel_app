package vn.travel.app.pages.main

import androidx.lifecycle.MutableLiveData
import vn.travel.app.base.BaseViewModel
import vn.travel.domain.model.AttractionModel

class RootViewModel : BaseViewModel() {
	val detail = MutableLiveData<AttractionModel?>(null)
}
