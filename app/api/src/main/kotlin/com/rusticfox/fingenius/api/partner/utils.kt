package com.rusticfox.fingenius.api.partner

import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.dto.PartnerRepresentativeResponseDto
import com.rusticfox.fingenius.api.partner.dto.PartnerResponseDto
import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber

fun Partner.toPartnerResponseDto(): PartnerResponseDto {
    return PartnerResponseDto(
        id = identifier,
        type = type.name,
        firstName = firstName.value,
        lastName = lastName.value,
        email = email.value,
        status = status.name,
        contactNo = contactNo.value,
        openingBalance = openingBalance.value,
        address = address,
        representative = PartnerRepresentativeResponseDto(
            name = representative.name.value,
            contact = representative.contact.value,
            designation = representative.designation
        ),
    )
}

fun PartnerDto.toPartner(): Partner {
    return Partner(
        type = PartnerType.valueOf(type.toString()),
        firstName = Name(firstName.toString()),
        lastName = Name(lastName.toString()),
        email = Email(email.toString()),
        contactNo = PhoneNumber(contactNo.toString()),
        address = address.toString(),
        representative = PartnerRepresentative(
            name = Name(representative.name),
            contact = PhoneNumber(representative.contact),
            designation = representative.designation
        )
    )
}
