package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.PartnerRepresentative

fun Partner.toPartnerModel(): PartnerModel {
    return PartnerModel(
        type = type,
        firstName = firstName,
        lastName = lastName,
        email = email,
        status = status,
        contactNo = contactNo,
        openingBalance = openingBalance,
        address = address,
        repName = representative.name,
        repDesignation = representative.designation,
        repContact = representative.contact
    )
}

fun PartnerModel.toPartner(): Partner {
    return Partner(
        partnerId = partnerId?.let { PartnerId(it) } ?: PartnerId(),
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
            designation=repDesignation,
            contact=repContact
        )
    )
}

fun mapToPartner(model: PartnerModel): Partner {
    return Partner(
        partnerId = model.partnerId?.let { PartnerId(it) } ?: PartnerId(),
        type = model.type,
        firstName = model.firstName,
        lastName = model.lastName,
        email = model.email,
        status = model.status,
        contactNo = model.contactNo,
        openingBalance = model.openingBalance,
        address = model.address,
        representative = PartnerRepresentative(
            name = model.repName,
            designation = model.repDesignation,
            contact = model.repContact
        )
    )
}
