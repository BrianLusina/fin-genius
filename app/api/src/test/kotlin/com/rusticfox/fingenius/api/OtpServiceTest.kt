package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.api.dto.OtpRequestDto
import com.rusticfox.fingenius.api.dto.OtpVerifyDto
import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.entities.OtpVerificationStatus
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.entities.VerifyOtpCode
import com.rusticfox.fingenius.core.services.CreateOtpService
import com.rusticfox.fingenius.core.services.VerifyOtpService
import io.mockk.confirmVerified
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.assertEquals

@Tag("unit")
class OtpServiceTest {
    private val mockCreateOtpService = mockk<com.rusticfox.fingenius.core.services.CreateOtpService>()
    private val mockVerifyService = mockk<com.rusticfox.fingenius.core.services.VerifyOtpService>()
    private val otpService by lazy {
        OtpService(
            mockCreateOtpService,
            mockVerifyService
        )
    }

    @Test
    fun `Should throw exception when there is a failure to coVerify OTP code`() {
        val otpCode = "908632"
        val userId = "254700000000"

        val coVerifyOtpCode = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = otpCode,
            userId = com.rusticfox.fingenius.core.entities.UserId(userId)
        )

        val otpVerifyDto = OtpVerifyDto(
            userId = userId,
            code = otpCode
        )

        coEvery {
            mockVerifyService.execute(any())
        } throws Exception("Failed verification")

        assertThrows<Exception> {
            runBlocking {
                otpService.verifyOtp(otpVerifyDto)
            }
        }

        coVerify {
            mockVerifyService.execute(coVerifyOtpCode)
        }

        confirmVerified(mockVerifyService)
    }

    @Test
    fun `Should return OTP Verification status when coVerify otp service succeeds`() {
        val otpCode = "908632"
        val userId = "254700000000"

        val verificationStatusList = listOf(
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED,
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.CODE_EXPIRED,
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.FAILED_VERIFICATION,
            com.rusticfox.fingenius.core.entities.OtpVerificationStatus.USER_NOT_FOUND
        )

        fun <E> List<E>.getRandomElement() = this[Random().nextInt(this.size)]

        val expectedResponse = verificationStatusList.getRandomElement()

        val coVerifyOtpCode = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = otpCode,
            userId = com.rusticfox.fingenius.core.entities.UserId(userId)
        )

        val otpVerifyDto = OtpVerifyDto(
            userId = userId,
            code = otpCode
        )

        coEvery {
            mockVerifyService.execute(any())
        } returns expectedResponse

        val actual = assertDoesNotThrow {
            runBlocking {
                otpService.verifyOtp(otpVerifyDto)
            }
        }

        assertEquals(expectedResponse, actual)

        coVerify {
            mockVerifyService.execute(coVerifyOtpCode)
        }

        confirmVerified(mockVerifyService)
    }

    @Test
    fun `Should throw exception if OTP generation fails`() {
        val userId = "254700000000"
        val otpRequestDto = OtpRequestDto(
            userId = userId
        )

        coEvery {
            mockCreateOtpService.execute(any())
        } throws Exception("Some Exception")

        assertThrows<Exception> {
            runBlocking {
                otpService.generateOtp(otpRequestDto)
            }
        }

        coVerify {
            mockCreateOtpService.execute(com.rusticfox.fingenius.core.entities.UserId(userId))
        }

        confirmVerified(mockVerifyService)
    }

    @Test
    fun `Should return OTP code upon successful generation of code`() {
        val userId = "254700000000"
        val otpRequestDto = OtpRequestDto(
            userId = userId
        )

        val code = "12345"
        val user = com.rusticfox.fingenius.core.entities.UserId(userId)
        val expiryTime = LocalDateTime(LocalDate(2023, 1, 1), LocalTime(1, 1, 1))

        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = user,
            expiryTime = expiryTime,
            used = false
        )

        coEvery {
            mockCreateOtpService.execute(any())
        } returns otpCode

        val actual = assertDoesNotThrow {
            runBlocking {
                otpService.generateOtp(otpRequestDto)
            }
        }

        assertEquals(otpCode, actual)

        coVerify {
            mockCreateOtpService.execute(com.rusticfox.fingenius.core.entities.UserId(userId))
        }

        confirmVerified(mockVerifyService)
    }
}
