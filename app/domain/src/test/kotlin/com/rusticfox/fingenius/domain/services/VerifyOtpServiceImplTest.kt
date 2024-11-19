package com.rusticfox.fingenius.domain.services

import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.VerifyOtpCode
import com.rusticfox.fingenius.core.ports.OtpDataStore
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coJustRun
import io.mockk.mockk
import io.mockk.coVerifySequence
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import kotlin.test.assertEquals

@Tag("unit")
class VerifyOtpServiceImplTest {
    private val mockDataStore = mockk<com.rusticfox.fingenius.core.ports.OtpDataStore>()
    private val coVerifyOtpService = VerifyOtpServiceImpl(mockDataStore)

    @Test
    fun `should throw NotFoundException if OTP can not be found`() {
        coEvery {
            mockDataStore.getOtpCode(any())
        } throws com.rusticfox.fingenius.core.exceptions.NotFoundException("OTP code not found")

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = "123456",
            userId = com.rusticfox.fingenius.core.entities.UserId("09876")
        )

        assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
            runBlocking {
                coVerifyOtpService.execute(request)
            }
        }

        coVerify {
            mockDataStore.getOtpCode(request.otpCode)
        }
    }

    @Test
    fun `should return OtpVerificationStatus_VERIFIED if OTP expiry time is after now`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("09876")
        val expiryTime = LocalDateTime.now().plusMinutes(5).toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime
        )

        val updatedOtpCode = otpCode.copy(used = true)

        coEvery {
            mockDataStore.getOtpCode(any())
        } returns otpCode

        coJustRun {
            mockDataStore.markOtpAsUsed(any())
        }

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = code,
            userId = userId
        )

        val actual = assertDoesNotThrow {
            runBlocking {
                coVerifyOtpService.execute(request)
            }
        }

        assertEquals(com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED, actual)

        coVerify {
            mockDataStore.getOtpCode(request.otpCode)
            mockDataStore.markOtpAsUsed(updatedOtpCode)
        }
    }

    @Test
    fun `should return OtpVerificationStatus_CODE_EXPIRED if OTP expiry time is before now`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("09876")
        val expiryTime = LocalDateTime.now().minusMinutes(10).toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime
        )

        coEvery {
            mockDataStore.getOtpCode(any())
        } returns otpCode

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = code,
            userId = userId
        )

        val actual = assertDoesNotThrow {
            runBlocking {
                coVerifyOtpService.execute(request)
            }
        }

        assertEquals(com.rusticfox.fingenius.core.entities.OtpVerificationStatus.CODE_EXPIRED, actual)

        coVerify {
            mockDataStore.getOtpCode(request.otpCode)
        }

        coVerify(exactly = 0) {
            mockDataStore.markOtpAsUsed(any())
        }
    }

    @Test
    fun `should return OtpVerificationStatus_FAILED_VERIFICATION if there is a failure marking otp as used`() {
        val code = "123456"
        val userId = com.rusticfox.fingenius.core.entities.UserId("09876")
        val expiryTime = LocalDateTime.now().plusMinutes(10).toKotlinLocalDateTime()

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime
        )
        val updatedOtpCode = otpCode.copy(used = true)

        coEvery {
            mockDataStore.getOtpCode(any())
        } returns otpCode

        coEvery {
            mockDataStore.markOtpAsUsed(any())
        } throws Exception("Failed to mark OTP as used")

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = code,
            userId = userId
        )

        val actual = assertDoesNotThrow {
            runBlocking {
                coVerifyOtpService.execute(request)
            }
        }

        assertEquals(com.rusticfox.fingenius.core.entities.OtpVerificationStatus.FAILED_VERIFICATION, actual)

        coVerifySequence {
            mockDataStore.getOtpCode(request.otpCode)
            mockDataStore.markOtpAsUsed(updatedOtpCode)
        }
    }
}
