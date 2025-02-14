package com.rusticfox.fingenius.core.ports.datastore.dto

data class PageRequestSort(
    val fields: PageSortFields = PageSortFields.CREATED_ON,
    val order: PageSortOrder = PageSortOrder.DESC
)

enum class PageSortFields {
    CREATED_ON,
    UPDATED_ON,
    DELETED_ON;

    companion object {
        fun fromString(value: String?): PageSortFields {
            return if (value != null) {
                runCatching { PageSortFields.valueOf(value.uppercase()) }.getOrElse { CREATED_ON }
            } else {
                CREATED_ON
            }
        }
    }
}

enum class PageSortOrder {
    ASC,
    DESC;
    
    companion object {
        fun fromString(value: String?): PageSortOrder {
            return if (value != null) {
                runCatching { PageSortOrder.valueOf(value.uppercase()) }.getOrElse { ASC }
            } else {
                ASC
            }
        }
    }
}
