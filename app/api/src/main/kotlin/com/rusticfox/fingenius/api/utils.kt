package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequest
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequestSort
import com.rusticfox.fingenius.core.ports.datastore.dto.PageSortFields
import com.rusticfox.fingenius.core.ports.datastore.dto.PageSortOrder
import io.ktor.http.Parameters
import kotlinx.datetime.LocalDateTime

fun constructPageRequestFromQueryParameters(queryParameters: Parameters): PageRequest {
    val size = queryParameters["size"]
    val number = queryParameters["number"]
    val includeDeleted = queryParameters["includeDeleted"]
    val sortOrder = queryParameters["order"]
    val sortBy = queryParameters["sortBy"]
    val startDate = queryParameters["startDate"]
    val endDate = queryParameters["endDate"]

    val pageSize = runCatching { size?.toInt() ?: 100 }.getOrElse { 100 }
    val pageNumber = runCatching { number?.toLong() ?: 0 }.getOrElse { 0 }
    val sortOrderField = PageSortOrder.fromString(sortOrder)
    val sortByField = PageSortFields.fromString(sortBy)
    val startingDate = startDate?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }
    val endingDate = endDate?.let { runCatching { LocalDateTime.parse(it) }.getOrNull() }

    return PageRequest(
        size = pageSize,
        number = pageNumber,
        includeDeleted = includeDeleted?.toBoolean() == true,
        sortFields = PageRequestSort(
            fields = sortByField,
            order = sortOrderField
        ),
        startDate = startingDate,
        endDate = endingDate
    )
}
