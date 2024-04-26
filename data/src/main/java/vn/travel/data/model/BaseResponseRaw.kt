package vn.travel.data.model

data class ObjectResponse<Raw>(
    val data: Raw? = null,
)

data class ListResponse<Raw>(
    var data: List<Raw>? = listOf(),
    val total: Int = 0,
)
