package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.testfixtures.data.testPartner
import com.rusticfox.fingenius.testfixtures.utils.TEST_TYPE_UNIT
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
import kotlin.test.assertIs

@Tag(TEST_TYPE_UNIT)
class PartnerReadDataStoreAdapterTest {
    private val mockPartnerRepository = mockk<PartnerRepository>()
    private val partnerReadDataStore = PartnerReadDataStoreAdapter(mockPartnerRepository)
    private val mockPartnerModel = PartnerModel(
        type = PartnerType.VENDOR,
        firstName = "Rustic",
        lastName = "Fox",
        email = "rusticfox@example.com",
        status = PartnerStatus.ACTIVE,
        contactNo = "254700000000",
        openingBalance = BigDecimal.valueOf(100.00),
        currency = "KES",
        address = "Some City",
        representative = PartnerRepresentativeModel(
            name = "Partner Rep",
            contact = "2547111111",
            designation = "Rep"
        )
    )

    @Test
    fun `should throw exception when there is a failure to fetch an partner given an ID`() = runTest {
        coEvery {
            mockPartnerRepository.findById(any())
        } throws Exception("failed to retrieve partner by ID")

        assertThrows<Exception> {
            partnerReadDataStore.findById(testPartner.partnerId.id)
        }

        coVerify {
            mockPartnerRepository.findById(testPartner.partnerId.id)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw exception when repository returns null when fetching a partner given an ID`() = runTest {
        coEvery {
            mockPartnerRepository.findById(any())
        } returns null

        assertThrows<DatabaseException> {
            partnerReadDataStore.findById(testPartner.partnerId.id)
        }

        coVerify {
            mockPartnerRepository.findById(testPartner.partnerId.id)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should return a partner when fetching a partner given an ID`() = runTest {
        coEvery {
            mockPartnerRepository.findById(any())
        } returns mockPartnerModel

        assertDoesNotThrow {
            partnerReadDataStore.findById(testPartner.partnerId.id)
        }

        coVerify {
            mockPartnerRepository.findById(testPartner.partnerId.id)
        }

        confirmVerified(mockPartnerRepository)
    }
}
