package com.rusticfox.fingenius.core.ports.datastore.dto

data class PageRequestSort(
    val fields: PageSortFields = PageSortFields.CREATED_ON,
    val order: PageSortOrder = PageSortOrder.DESC
)

enum class PageSortFields {
    CREATED_ON,
    UPDATED_ON,
    DELETED_ON
}

enum class PageSortOrder {
    ASC,
    DESC
}
