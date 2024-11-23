package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
import com.rusticfox.fingenius.api.dto.PartnerRepresentativeDto
import com.rusticfox.fingenius.api.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import java.util.*

fun Partner.toPartnerResponseDto(): PartnerResponseDto {
    return PartnerResponseDto(
        type = type.name,
        firstName = firstName.value,
        lastName = lastName.value,
        email = email.value,
        status = status.name,
        contactNo = contactNo.value,
        openingBalance = openingBalance.value,
        address = address,
        representative = PartnerRepresentativeDto(
            name = representative.name.value,
            contact = representative.contact.value,
            designation = representative.designation
        ),
    )
}

fun CreatePartnerRequestDto.toPartner(): Partner {
    return Partner(
        type = PartnerType.valueOf(type),
        firstName = Name(firstName),
        lastName = Name(lastName),
        email = Email(email),
        contactNo = PhoneNumber(contactNo),
        address = address,
        representative = PartnerRepresentative(
            name = Name(representative.name),
            contact = PhoneNumber(representative.contact),
            designation = representative.designation
        )
    )
}
