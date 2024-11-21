package com.rusticfox.fingenius.api

import com.rusticfox.fingenius.api.dto.OtpRequestDto
import com.rusticfox.fingenius.api.dto.OtpVerifyDto
import com.rusticfox.fingenius.api.partner.PartnerService
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import io.mockk.confirmVerified
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@Tag("unit")
class OtpRestApiTest : KoinTest {
    private val mockPartnerService = mockk<PartnerService>()

    @BeforeEach
    fun before() {
        startKoin {
            modules(
                module {
                    single { mockPartnerService }
                }
            )
        }
    }

    @AfterEach
    fun after() {
        stopKoin()
    }

    @Test
    fun `should return generated OTP code`() = testApplication {
        application {
            configureRouting()
        }

        val testHttpClient = createClient {
            install(plugin = ContentNegotiation) {
                json()
            }
        }

        val userId = "132456"
        val request = OtpRequestDto(userId)

        val code = "45468"
        val expiryTime = LocalDateTime(LocalDate(2023, 1, 1), LocalTime(1, 1, 1))
        val used = false
        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = com.rusticfox.fingenius.core.entities.UserId(userId),
            expiryTime = expiryTime,
            used = used
        )

        coEvery {
            mockPartnerService.createPartner(any())
        } returns otpCode

        val expectedResponse = """
            {
                "userId": "$userId",
                "code": "$code",
                "expiryTime": "$expiryTime"
            }
        """.trimIndent()

        testHttpClient.post("/api/v1/otp") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
            .apply {
                assertEquals(HttpStatusCode.Created, status)
                assertEquals(expectedResponse, bodyAsText())
            }

        coVerify {
            mockPartnerService.createPartner(otpRequestDto = request)
        }

        confirmVerified(mockPartnerService)
    }

    @Test
    fun `should return error when there is a failure generating OTP code`() = testApplication {
        application {
            configureRouting()
        }
        val testHttpClient = createClient {
            install(plugin = ContentNegotiation) {
                json()
            }
        }

        val userId = "132456"
        val request = OtpRequestDto(userId)

        coEvery {
            mockPartnerService.createPartner(any())
        } throws Exception("Failed to generated OTP code")

        val expectedResponse = """
            {
                "message": "Failed to generated OTP code"
            }
        """.trimIndent()

        testHttpClient.post("/api/v1/otp") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
            .apply {
                assertEquals(HttpStatusCode.InternalServerError, status)
                assertEquals(expectedResponse, bodyAsText())
            }

        coVerify {
            mockPartnerService.createPartner(otpRequestDto = request)
        }

        confirmVerified(mockPartnerService)
    }

    @Test
    fun `should return success response when there is a success coVerifying OTP code`() = testApplication {
        application {
            configureRouting()
        }
        val testHttpClient = createClient {
            install(plugin = ContentNegotiation) {
                json()
            }
        }

        val userId = "132456"
        val code = "132456"
        val request = OtpVerifyDto(userId = userId, code = code)
        val verificationStatus = com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED

        coEvery {
            mockPartnerService.verifyOtp(any())
        } returns verificationStatus

        val expectedResponse = """
            {
                "userId": "$userId",
                "code": "$code",
                "status": "$verificationStatus"
            }
        """.trimIndent()

        testHttpClient.post("/api/v1/otp/verify") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
            .apply {
                assertEquals(HttpStatusCode.OK, status)
                assertEquals(expectedResponse, bodyAsText())
            }

        coVerify {
            mockPartnerService.verifyOtp(verifyOtpDto = request)
        }

        confirmVerified(mockPartnerService)
    }

    @Test
    fun `should return error response when there is a failure coVerifying OTP code`() = testApplication {
        application {
            configureRouting()
        }
        val testHttpClient = createClient {
            install(plugin = ContentNegotiation) {
                json()
            }
        }

        val userId = "132456"
        val code = "132456"
        val request = OtpVerifyDto(userId = userId, code = code)

        val errorMessage = "Failed to coVerify OTP code $code"
        coEvery {
            mockPartnerService.verifyOtp(any())
        } throws Exception(errorMessage)

        val expectedResponse = """
            {
                "message": "$errorMessage"
            }
        """.trimIndent()

        testHttpClient.post("/api/v1/otp/verify") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
            .apply {
                assertEquals(HttpStatusCode.InternalServerError, status)
                assertEquals(expectedResponse, bodyAsText())
            }

        coVerify {
            mockPartnerService.verifyOtp(verifyOtpDto = request)
        }

        confirmVerified(mockPartnerService)
    }
}
