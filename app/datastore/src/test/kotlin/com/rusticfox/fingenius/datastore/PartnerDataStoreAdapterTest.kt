package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.datastore.models.InvoiceItemModel
import com.rusticfox.fingenius.datastore.partner.PartnerRepository
import com.rusticfox.fingenius.datastore.partner.PartnerDataStoreAdapter
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.coVerifySequence
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertEquals

@Tag("unit")
class PartnerDataStoreAdapterTest {
    private val mockPartnerRepository = mockk<PartnerRepository>()
    private val otpDataStore = PartnerDataStoreAdapter(mockPartnerRepository)

    @Test
    fun `should throw exception when there is a failure to create new OTP code`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now().toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime
        )

        coEvery {
            PartnerRepository.insert(any())
        } throws Exception("failed to insert record")

        assertThrows<com.rusticfox.fingenius.core.exceptions.DatabaseException> {
            runBlocking {
                otpDataStore.create(otpCode)
            }
        }

        coVerify {
            PartnerRepository.insert(otpCode)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should return otp code on successful creation of OTP`() {
        val generatedCode = "123456"
        val otpUserId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val otpExpiryTime = LocalDateTime.now().toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = generatedCode,
            userId = otpUserId,
            expiryTime = otpExpiryTime
        )

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            PartnerRepository.insert(any())
        } returns mockInvoiceItemModel

        assertDoesNotThrow {
            runBlocking {
                otpDataStore.create(otpCode)
            }
        }

        coVerify {
            PartnerRepository.insert(otpCode)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw NotFoundException when marking otp code as used if it does not exist`() {
        val generatedCode = "123456"
        val otpUserId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val otpExpiryTime = LocalDateTime.now().toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = generatedCode,
            userId = otpUserId,
            expiryTime = otpExpiryTime
        )

        coEvery {
            PartnerRepository.findByCode(any())
        } returns null

        assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
            runBlocking {
                otpDataStore.markOtpAsUsed(otpCode)
            }
        }

        coVerify {
            PartnerRepository.findByCode(generatedCode)
        }

        coVerify(exactly = 0) {
            PartnerRepository.markAsUsed(any())
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw DatabaseException when there is a failure marking otp code as used`() {
        val generatedCode = "123456"
        val otpUserId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val otpExpiryTime = LocalDateTime.now().toKotlinLocalDateTime()
        val used = true

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = generatedCode,
            userId = otpUserId,
            expiryTime = otpExpiryTime,
            used = used
        )

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            PartnerRepository.findByCode(any())
        } returns mockInvoiceItemModel

        coEvery {
            PartnerRepository.markAsUsed(generatedCode)
        } throws Exception("Failed to update otp")

        assertThrows<com.rusticfox.fingenius.core.exceptions.DatabaseException> {
            runBlocking {
                otpDataStore.markOtpAsUsed(otpCode)
            }
        }

        coVerifySequence {
            PartnerRepository.findByCode(generatedCode)
            PartnerRepository.markAsUsed(generatedCode)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should not throw DatabaseException when there is a success marking otp code as used`() {
        val generatedCode = "123456"
        val otpUserId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val otpExpiryTime = LocalDateTime.now().toKotlinLocalDateTime()
        val used = true

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = generatedCode,
            userId = otpUserId,
            expiryTime = otpExpiryTime,
            used = used
        )

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            PartnerRepository.findByCode(any())
        } returns mockInvoiceItemModel

        coEvery {
            PartnerRepository.markAsUsed(any())
        } returns 1

        assertDoesNotThrow {
            runBlocking {
                otpDataStore.markOtpAsUsed(otpCode)
            }
        }

        coVerifySequence {
            PartnerRepository.findByCode(generatedCode)
            PartnerRepository.markAsUsed(generatedCode)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw NotFoundException when retrieving an OTP by code & it's non-existent`() {
        val generatedCode = "123456"

        coEvery {
            PartnerRepository.findByCode(any())
        } returns null

        assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
            runBlocking {
                otpDataStore.getOtpCode(generatedCode)
            }
        }

        coVerify {
            PartnerRepository.findByCode(generatedCode)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should return OTP when retrieving it by code & it exists`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now()
        val used = false

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            mockInvoiceItemModel.code
        } returns code

        coEvery {
            mockInvoiceItemModel.used
        } returns used

        coEvery {
            mockInvoiceItemModel.userId
        } returns userId.value

        coEvery {
            mockInvoiceItemModel.expiryTime
        } returns expiryTime

        coEvery {
            PartnerRepository.findByCode(any())
        } returns mockInvoiceItemModel

        val actual = assertDoesNotThrow {
            runBlocking {
                otpDataStore.getOtpCode(code)
            }
        }

        assertEquals(code, actual.code)
        assertEquals(used, actual.used)
        assertEquals(userId, actual.userId)
        assertEquals(expiryTime, actual.expiryTime.toJavaLocalDateTime())

        coVerify {
            PartnerRepository.findByCode(code)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should return Collection of OTPs when retrieving all codes`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now()
        val used = false

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            mockInvoiceItemModel.code
        } returns code

        coEvery {
            mockInvoiceItemModel.used
        } returns used

        coEvery {
            mockInvoiceItemModel.userId
        } returns userId.value

        coEvery {
            mockInvoiceItemModel.expiryTime
        } returns expiryTime

        val otpCodes = listOf(mockInvoiceItemModel)

        coEvery {
            PartnerRepository.findAll()
        } returns otpCodes

        val actual = assertDoesNotThrow {
            runBlocking {
                otpDataStore.getAll()
            }
        }

        assertEquals(otpCodes.size, actual.size)

        coVerify {
            PartnerRepository.findAll()
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw exception when there is a failure retrieving all OTP codes`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now()
        val used = false

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            mockInvoiceItemModel.code
        } returns code

        coEvery {
            mockInvoiceItemModel.used
        } returns used

        coEvery {
            mockInvoiceItemModel.userId
        } returns userId.value

        coEvery {
            mockInvoiceItemModel.expiryTime
        } returns expiryTime

        coEvery {
            PartnerRepository.findAll()
        } throws Exception("Failed to retrieve all OTP codes")

        assertThrows<com.rusticfox.fingenius.core.exceptions.DatabaseException> {
            runBlocking {
                otpDataStore.getAll()
            }
        }

        coVerify {
            PartnerRepository.findAll()
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should throw exception when there is a failure retrieving all OTP codes by user ID`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now()
        val used = false

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            mockInvoiceItemModel.code
        } returns code

        coEvery {
            mockInvoiceItemModel.used
        } returns used

        coEvery {
            mockInvoiceItemModel.userId
        } returns userId.value

        coEvery {
            mockInvoiceItemModel.expiryTime
        } returns expiryTime

        coEvery {
            PartnerRepository.findAllByUserId(any())
        } throws Exception("Failed to retrieve all OTP codes")

        assertThrows<com.rusticfox.fingenius.core.exceptions.DatabaseException> {
            runBlocking {
                otpDataStore.getAllByUserId(userId)
            }
        }

        coVerify {
            PartnerRepository.findAllByUserId(userId.value)
        }

        confirmVerified(mockPartnerRepository)
    }

    @Test
    fun `should retrieve collection of OTP codes for a user ID`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("654321")
        val expiryTime = LocalDateTime.now()
        val used = false

        val mockInvoiceItemModel = mockk<InvoiceItemModel>(relaxed = true)

        coEvery {
            mockInvoiceItemModel.code
        } returns code

        coEvery {
            mockInvoiceItemModel.used
        } returns used

        coEvery {
            mockInvoiceItemModel.userId
        } returns userId.value

        coEvery {
            mockInvoiceItemModel.expiryTime
        } returns expiryTime

        val otpCodes = listOf(mockInvoiceItemModel)

        coEvery {
            PartnerRepository.findAllByUserId(any())
        } returns otpCodes

        assertDoesNotThrow {
            runBlocking {
                otpDataStore.getAllByUserId(userId)
            }
        }

        coVerify {
            PartnerRepository.findAllByUserId(userId.value)
        }

        confirmVerified(mockPartnerRepository)
    }
}
