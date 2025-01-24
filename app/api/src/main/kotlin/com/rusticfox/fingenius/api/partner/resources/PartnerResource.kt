package com.rusticfox.fingenius.api.partner.resources

import io.ktor.resources.Resource
import kotlinx.datetime.LocalDate

@Resource("/partners")
class PartnerResource(
    val pageSize: Int? = 100,
    val pageNumber: Int? = 0,
    val includeDeleted: Boolean? = false,
    val sortOrder: String? = "desc",
    val sortBy: String? = "created_on",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
) {

}