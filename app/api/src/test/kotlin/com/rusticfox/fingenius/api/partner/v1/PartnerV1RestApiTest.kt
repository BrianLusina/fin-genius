package com.rusticfox.fingenius.api.partner.v1

import com.rusticfox.fingenius.api.customTestApplication
import com.rusticfox.fingenius.api.partner.dto.CreatePartnerRepresentativeDto
import com.rusticfox.fingenius.api.partner.dto.PartnerResponseDto
import com.rusticfox.fingenius.api.partner.PartnerService
import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.dto.PartnerRepresentativeResponseDto
import com.rusticfox.fingenius.api.serializers.OptionalProperty
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import io.mockk.confirmVerified
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.coVerify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.math.BigDecimal

@Tag("unit")
class PartnerV1RestApiTest : KoinTest {
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
    fun `should return the created partner on a successful request and creation of record`() = customTestApplication { testHttpClient ->

        val partnerType = "vendor"
        val firstName = "Sly"
        val lastName = "Wolf"
        val email = "sly@wolf.com"
        val contactNo = "254722222222"
        val address = "FoxHole"
        val repDesignation = "Rep"
        val repContact = "254744444444"
        val repName = "Jon"

        val payload = """
            {
                "type": "$partnerType",
                "firstName": "$firstName",
                "lastName": "$lastName",
                "email": "$email",
                "contactNo": "$contactNo",
                "address" = "$address",
                "representative": {
                    "designation": "$repDesignation",
                    "contact": "$repContact",
                    "name": "$repName"
                }
            }
        """.trimIndent()

        val representative = PartnerRepresentativeResponseDto(
            designation = "Rep",
            contact = "254744444444",
            name = "Jon",
        )

        val expectedResponse = PartnerResponseDto(
            type = "vendor",
            firstName = "Sly",
            lastName = "Wolf",
            email = "sly@wolf.com",
            contactNo = "254722222222",
            address = "FoxHole",
            representative = representative,
            openingBalance = BigDecimal(0),
            status = "active",
            id = ""
        )

        coEvery {
            mockPartnerService.createPartner(any())
        } returns expectedResponse

        testHttpClient.post("/api/v1/partner/") {
            contentType(ContentType.Application.Json)
            setBody(payload)
        }
            .apply {
                assertEquals(HttpStatusCode.Created, status)
                assertEquals(expectedResponse, body())
            }

        coVerify {
            mockPartnerService.createPartner(request)
        }

        confirmVerified(mockPartnerService)
    }
//
//    @Test
//    fun `should return error when there is a failure generating OTP code`() = customTestApplication {
//        application {
//            configureRouting()
//        }
//        val testHttpClient = createClient {
//            install(plugin = ContentNegotiation) {
//                json()
//            }
//        }
//
//        val userId = "132456"
//        val request = OtpRequestDto(userId)
//
//        coEvery {
//            mockPartnerService.createPartner(any())
//        } throws Exception("Failed to generated OTP code")
//
//        val expectedResponse = """
//            {
//                "message": "Failed to generated OTP code"
//            }
//        """.trimIndent()
//
//        testHttpClient.post("/api/v1/otp") {
//            contentType(ContentType.Application.Json)
//            setBody(request)
//        }
//            .apply {
//                assertEquals(HttpStatusCode.InternalServerError, status)
//                assertEquals(expectedResponse, bodyAsText())
//            }
//
//        coVerify {
//            mockPartnerService.createPartner(otpRequestDto = request)
//        }
//
//        confirmVerified(mockPartnerService)
//    }
//
//    @Test
//    fun `should return success response when there is a success coVerifying OTP code`() = customTestApplication {
//        application {
//            configureRouting()
//        }
//        val testHttpClient = createClient {
//            install(plugin = ContentNegotiation) {
//                json()
//            }
//        }
//
//        val userId = "132456"
//        val code = "132456"
//        val request = OtpVerifyDto(userId = userId, code = code)
//        val verificationStatus = com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED
//
//        coEvery {
//            mockPartnerService.verifyOtp(any())
//        } returns verificationStatus
//
//        val expectedResponse = """
//            {
//                "userId": "$userId",
//                "code": "$code",
//                "status": "$verificationStatus"
//            }
//        """.trimIndent()
//
//        testHttpClient.post("/api/v1/otp/verify") {
//            contentType(ContentType.Application.Json)
//            setBody(request)
//        }
//            .apply {
//                assertEquals(HttpStatusCode.OK, status)
//                assertEquals(expectedResponse, bodyAsText())
//            }
//
//        coVerify {
//            mockPartnerService.verifyOtp(verifyOtpDto = request)
//        }
//
//        confirmVerified(mockPartnerService)
//    }
//
//    @Test
//    fun `should return error response when there is a failure coVerifying OTP code`() = customTestApplication {
//        application {
//            configureRouting()
//        }
//        val testHttpClient = createClient {
//            install(plugin = ContentNegotiation) {
//                json()
//            }
//        }
//
//        val userId = "132456"
//        val code = "132456"
//        val request = OtpVerifyDto(userId = userId, code = code)
//
//        val errorMessage = "Failed to coVerify OTP code $code"
//        coEvery {
//            mockPartnerService.verifyOtp(any())
//        } throws Exception(errorMessage)
//
//        val expectedResponse = """
//            {
//                "message": "$errorMessage"
//            }
//        """.trimIndent()
//
//        testHttpClient.post("/api/v1/otp/verify") {
//            contentType(ContentType.Application.Json)
//            setBody(request)
//        }
//            .apply {
//                assertEquals(HttpStatusCode.InternalServerError, status)
//                assertEquals(expectedResponse, bodyAsText())
//            }
//
//        coVerify {
//            mockPartnerService.verifyOtp(verifyOtpDto = request)
//        }
//
//        confirmVerified(mockPartnerService)
//    }
}
