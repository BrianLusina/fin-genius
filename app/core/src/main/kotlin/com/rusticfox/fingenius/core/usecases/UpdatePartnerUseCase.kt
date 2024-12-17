package com.rusticfox.fingenius.core.usecases

import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.interactor.UseCase
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.PhoneNumber
import com.rusticfox.fingenius.core.values.Name

data class UpdatePartnerRequest(
    val partnerId: PartnerId,
    val type: PartnerType? = null,
    val firstName: Name? = null,
    val lastName: Name? = null,
    val email: Email? = null,
    val contactNo: PhoneNumber? = null,
    val openingBalance: Amount? = null,
    val address: String? = null,
    val representative: PartnerRepresentative? = null,
    val status: PartnerStatus? = null
)

/**
 * Edits a partner
 */
interface UpdatePartnerUseCase: UseCase<UpdatePartnerRequest, Partner> {
    override suspend operator fun invoke(request: UpdatePartnerRequest): Partner
}
