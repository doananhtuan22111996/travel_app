package vn.travel.data.model

import com.google.gson.annotations.SerializedName
import vn.travel.domain.model.AttractionModel
import vn.travel.domain.model.CategoryModel
import vn.travel.domain.model.ImageModel
import vn.travel.domain.model.ServiceModel

data class AttractionRaw(
    private val id: Int,
    private val name: String?,
    @SerializedName("name_zh") private val nameZh: String?,
    @SerializedName("open_status") private val openStatus: Int?,
    private val introduction: String?,
    @SerializedName("open_time") private val openTime: String?,
    private val zipcode: String?,
    private val distric: String?,
    private val address: String?,
    private val tel: String?,
    private val fax: String?,
    private val email: String?,
    private val months: String?,
    @SerializedName("nlat") private val nLat: Double?,
    @SerializedName("elong") private val eLong: Double?,
    @SerializedName("official_site") private val officialSite: String?,
    private val facebook: String?,
    private val ticket: String?,
    private val remind: String?,
    @SerializedName("staytime") private val stayTime: String?,
    private val modified: String?,
    private val url: String?,
    private val category: List<CategoryRaw> = listOf(),
    private val target: List<TargetRaw> = listOf(),
    private val service: List<ServiceRaw> = listOf(),
    private val friendly: List<String> = listOf(),
    private val images: List<ImageRaw> = listOf(),
    private val files: List<String> = listOf(),
    private val links: List<String> = listOf()
) : BaseRaw() {
    override fun raw2Model(): AttractionModel = AttractionModel(
        id,
        name ?: "",
        nameZh ?: "",
        openStatus ?: 0,
        introduction ?: "",
        openTime ?: "",
        zipcode ?: "",
        distric ?: "",
        address ?: "",
        tel ?: "",
        fax ?: "",
        email ?: "",
        months ?: "",
        nLat ?: 0.0,
        eLong ?: 0.0,
        officialSite ?: "",
        facebook ?: "",
        ticket ?: "",
        remind ?: "",
        modified ?: "",
        url ?: "",
        category.map { it.raw2Model() },
        target.map { it.raw2Model() },
        service.map { it.raw2Model() },
        friendly,
        images.map { it.raw2Model() },
        files,
        links
    )
}