package vn.travel.data.model

import vn.travel.domain.model.ServiceModel

class ServiceRaw(private val id: Int, private val name: String?) : BaseRaw() {
    override fun raw2Model(): ServiceModel = ServiceModel(id, name ?: "")
}