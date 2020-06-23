package com.frank.mindvalley.network.response

import com.squareup.moshi.Json

data class CategoryResponseData(
    @Json(name = "data") val categoryResponse: CategoryResponse? = null
)

data class CategoryResponse(
    @Json(name = "categories") val categories: List<CategoryNetwork>?
)

data class CategoryNetwork(
    @Json(name = "name") val name: String? = null
)