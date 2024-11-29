package com.rusticfox.fingenius.testfixtures.data

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import java.math.BigDecimal
import java.util.Currency


val testPartner = Partner(
    type = PartnerType.VENDOR,
    firstName = Name("Rustic"),
    lastName = Name("Fox"),
    email = Email("rusticfox@example.com"),
    status = PartnerStatus.ACTIVE,
    contactNo = PhoneNumber("254700000000"),
    openingBalance = Amount(currency = Currency.getInstance("KES"), value = BigDecimal.valueOf(100.00)),
    address = "Some City",
    representative = PartnerRepresentative(
        name = Name("Partner Rep"),
        contact = PhoneNumber("2547111111"),
        designation = "Rep"
    )
)
