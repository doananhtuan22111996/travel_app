package vn.travel.data.model

import vn.travel.domain.model.ImageModel

class ImageRaw(
    private val src: String?,
    private val subject: String?,
    private val ext: String?,
) : BaseRaw() {
    override fun raw2Model(): ImageModel = ImageModel(src ?: "", subject ?: "", ext ?: "")
}