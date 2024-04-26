package vn.travel.data.model

import vn.travel.domain.model.TargetModel

class TargetRaw(private val id: Int, private val name: String?) : BaseRaw() {
    override fun raw2Model(): TargetModel = TargetModel(id, name ?: "")
}