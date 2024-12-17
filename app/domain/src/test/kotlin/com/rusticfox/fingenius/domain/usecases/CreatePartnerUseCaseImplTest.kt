package com.rusticfox.fingenius.domain.usecases

import com.rusticfox.fingenius.core.entities.Partner
import com.rusticfox.fingenius.core.entities.PartnerRepresentative
import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.values.Amount
import com.rusticfox.fingenius.core.values.Email
import com.rusticfox.fingenius.core.values.Name
import com.rusticfox.fingenius.core.values.PhoneNumber
import com.rusticfox.fingenius.domain.port.outbound.datastore.PartnerDataStorePort
import com.rusticfox.fingenius.domain.usecases.partner.create.CreatePartnerUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Currency

@Tag("unit")
class CreatePartnerUseCaseImplTest {
    private val mockDataStore = mockk<PartnerDataStorePort>()
    private val createPartnerUseCase = CreatePartnerUseCaseImpl(
        mockDataStore
    )

    private val testPartner = Partner(
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
    fun `should throw error if there is a failure creating a new user`() = runTest {
        coEvery {
            mockDataStore.create(any())
        } throws Exception("Failed to generate OTP code")


        assertThrows<Exception> {
            createPartnerUseCase(testPartner)
        }

        coVerify {
            mockDataStore.create(testPartner)
        }
    }

    @Test
    fun `should return created user `() = runTest {
        coEvery {
            mockDataStore.create(any())
        } returns testPartner

        assertDoesNotThrow {
            createPartnerUseCase(testPartner)
        }

        coVerify {
            mockDataStore.create(testPartner)
        }
    }
}
