package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import java.util.Currency

fun Partner.toPartnerModel(): PartnerModel {
    return PartnerModel(
        type = type,
        firstName = firstName.value,
        lastName = lastName.value,
        email = email.value,
        status = status,
        contactNo = contactNo.value,
        currency = openingBalance.currency.currencyCode,
        openingBalance = openingBalance.value,
        address = address,
        representative = PartnerRepresentativeModel(
            name = representative.name.value,
            designation = representative.designation,
            contact = representative.contact.value
        ),
    )
}

fun PartnerModel.toPartner(): Partner {
    return Partner(
        partnerId = partnerId?.let { PartnerId(it) } ?: PartnerId(),
        type = type,
        firstName = Name(firstName),
        lastName = Name(lastName),
        email = Email(email),
        status = status,
        contactNo = PhoneNumber(contactNo),
        openingBalance = Amount(currency= Currency.getInstance(currency), value = openingBalance),
        address = address,
        representative = PartnerRepresentative(
            name = Name(representative.name),
            designation = representative.designation,
            contact = PhoneNumber(representative.contact)
        )
    )
}
