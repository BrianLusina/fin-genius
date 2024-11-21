package com.rusticfox.fingenius.core.ports.datastore.dto

import kotlinx.datetime.LocalDateTime

/**
 * Defines a Page request when querying for a collection of data items. This can be included in the request to a given
 * service alongside other parameters.
 * Note that the fields provided are nullable, if they are null, then they are ignored and all records could potentially
 * be returned if there are no reasonable defaults defined.
 * @param size [Int] defines the page size of the request, It should be greater than 0
 * @param number [Int] defines the current page of the request, it should be equal to or greater than 0.
 * @param includeDeleted [Boolean] whether to include deleted records, the default is false
 * @param startDate [LocalDateTime] the minimum date for a search range
 * @param endDate [LocalDateTime] the maximum date for a search range(inclusive). If the end date is not
 * provided this defaults to now and ensures that the current date is equal to start date
 */
data class PageRequest(
    val size: Int = 100,
    val number: Long = 0,
    val includeDeleted: Boolean = false,
    val sortFields: PageRequestSort = PageRequestSort(),
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
) {
    init {
        require(size > 0) { "Size $size can not be less than or equal to 0" }
        require(number >= 0) { "Page $number can not be less than 0" }
        // if both are provided
        if (startDate != null && endDate != null) {
            require(endDate >= startDate) {
                "Start date $startDate cannot be after end date $endDate in date range"
            }
        }

        // if the end date is provided and the start date is not provided, we throw an exception, we will always require
        // a start date to limit the request
        if (endDate != null) {
            requireNotNull(startDate) {
                "End date $endDate has been provided without a start date"
            }
        }
    }
}
