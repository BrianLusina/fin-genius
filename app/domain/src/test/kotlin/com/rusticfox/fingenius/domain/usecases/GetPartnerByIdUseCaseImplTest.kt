package com.rusticfox.fingenius.domain.usecases

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerId
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerReadDataStorePort
import com.rusticfox.fingenius.domain.usecases.partner.GetPartnerByIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Currency

@Tag("unit")
class GetPartnerByIdUseCaseImplTest {
    private val mockDataStore = mockk<PartnerReadDataStorePort>()
    private val getPartnerByIdUseCase = GetPartnerByIdUseCaseImpl(
        mockDataStore
    )

    private val testPartnerId = PartnerId()
    private val testPartner = Partner(
        partnerId = testPartnerId,
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

    @Test
    fun `should throw error if there is a failure fetching a partner given their ID`() = runTest {
        coEvery {
            mockDataStore.findById(any())
        } throws Exception("Failed to retrieve partner given their ID $testPartnerId")

        assertThrows<Exception> {
            getPartnerByIdUseCase(testPartnerId)
        }

        coVerify {
            mockDataStore.findById(testPartnerId.id)
        }

        confirmVerified(mockDataStore)
    }

    @Test
    fun `should return existing partner if exists`() = runTest {
        coEvery {
            mockDataStore.findById(any())
        } returns testPartner

        assertDoesNotThrow {
            getPartnerByIdUseCase(testPartnerId)
        }

        coVerify {
            mockDataStore.findById(testPartnerId.id)
        }

        confirmVerified(mockDataStore)
    }

    @Test
    fun `should throw NotFoundException if partner does not exist`() = runTest {
        coEvery {
            mockDataStore.findById(any())
        } returns null

        assertThrows<NotFoundException> {
            getPartnerByIdUseCase(testPartnerId)
        }

        coVerify {
            mockDataStore.findById(testPartnerId.id)
        }

        confirmVerified(mockDataStore)
    }
}
