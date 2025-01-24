package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import io.ktor.http.Parameters

fun constructPageRequestFromQueryParameters(queryParameters: Parameters): PageRequest {
    val size = queryParameters["size"]
    val number = queryParameters["number"]
    val includeDeleted = queryParameters["includeDeleted"]
    val sortOrder = queryParameters["order"]
    val sortBy = queryParameters["sortBy"]
    val startDate = queryParameters["startDate"]
    val endDate = queryParameters["endDate"]

    val pageSize = if (size != null) {
        runCatching { size.toInt() }.getOrElse { 100 }
    } else { 100 }

    return PageRequest(
        size = pageSize
    )
}
