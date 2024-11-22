package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
import com.rusticfox.fingenius.api.dto.PartnerRepresentativeResponseDto
import com.rusticfox.fingenius.api.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerRepresentative

fun Partner.toPartnerResponseDto(): PartnerResponseDto {
    return PartnerResponseDto(
        type = type,
        firstName = firstName,
        lastName = lastName,
        email = email,
        status = status,
        contactNo = contactNo,
        openingBalance = openingBalance,
        address = address,
        representative = PartnerRepresentativeResponseDto(
            name = representative.name,
            contact = representative.contact,
            designation = representative.designation
        ),
    )
}

fun CreatePartnerRequestDto.toPartner(): Partner {
    return Partner(
        type = type,
        firstName = firstName,
        lastName = lastName,
        email = email,
        status = status,
        contactNo = contactNo,
        openingBalance = openingBalance,
        address = address,
        representative = PartnerRepresentative(
            name = repName,
            contact = repContact,
            designation = repDesignation
        )
    )
}
