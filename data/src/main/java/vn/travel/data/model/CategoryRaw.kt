package vn.travel.data.model

import vn.travel.domain.model.CategoryModel

data class CategoryRaw(private val id: Int, private val name: String?) : BaseRaw() {
    override fun raw2Model(): CategoryModel = CategoryModel(id, name ?: "")
}