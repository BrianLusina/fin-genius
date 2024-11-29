package com.rusticfox.fingenius.datastore.partner

import com.rusticfox.fingenius.core.entities.PartnerStatus
import com.rusticfox.fingenius.core.entities.PartnerType
import com.rusticfox.fingenius.testfixtures.data.testPartner
import com.rusticfox.fingenius.testfixtures.utils.TEST_TYPE_UNIT
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

@Tag(TEST_TYPE_UNIT)
class PartnerWriteDataStoreAdapterTest {
    private val mockPartnerRepository = mockk<PartnerRepository>()
    private val partnerWriteDataStore = PartnerWriteDataStoreAdapter(mockPartnerRepository)
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
    fun `should throw exception when there is a failure to create new partner`() = runTest {
        coEvery {
            mockPartnerRepository.insert(any())
        } throws Exception("failed to insert record")

        assertThrows<Exception> {
            partnerWriteDataStore.create(testPartner)
        }

        coVerify {
            mockPartnerRepository.insert(testPartner)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should return partner code on successful creation of Partner`() = runTest {
        coEvery {
            mockPartnerRepository.insert(any())
        } returns mockPartnerModel

        assertDoesNotThrow {
            partnerWriteDataStore.create(testPartner)
        }

        coVerify {
            mockPartnerRepository.insert(testPartner)
        }

        confirmVerified(mockPartnerRepository)
    }
}
