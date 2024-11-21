package com.rusticfox.fingenius.domain

import com.rusticfox.fingenius.datastore.partner.PartnerDataStoreAdapter
import com.rusticfox.fingenius.datastore.partner.PartnerRepository
import com.rusticfox.fingenius.datastore.models.InvoiceItemModel
import com.sanctumlabs.otp.domain.services.VerifyOtpServiceImpl
import com.sanctumlabs.otp.testfixtures.utils.generateRandomString
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.component.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.days

@Tag("integration")
class VerifyPartnerServiceIntegrationTest : BaseIntegrationTest(), KoinTest {
    private val otpDatastore by inject<com.rusticfox.fingenius.core.ports.OtpDataStore>()
    private val partnerRepository by inject<PartnerRepository>()
    private val verifyOtpService by inject<VerifyOtpServiceImpl>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single { PartnerRepository }
                single<com.rusticfox.fingenius.core.ports.OtpDataStore> { PartnerDataStoreAdapter(get()) }
                single { VerifyOtpServiceImpl(get()) }
            })
    }

    @Test
    fun `context is created for the test`() {
        assertNotNull(partnerRepository)
        assertNotNull(otpDatastore)
        assertNotNull(verifyOtpService)
    }

    @Test
    fun `should return CODE_EXPIRED when verifying otp code for user id that was initially generated`() {
        val code = generateRandomString(6)
        val userCode = generateRandomString(12)
        val userId = com.rusticfox.fingenius.core.entities.UserId(value = userCode)
        val expiryTime = LocalDateTime(2023, 1, 1, 1, 1)
        val used = false
        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime,
            used = used
        )

        // insert an existing record
        val savedOtpRecord = runBlocking {
            assertDoesNotThrow {
                partnerRepository.insert(otpCode)
            }
        }

        assertNotNull(savedOtpRecord)
        assertEquals(savedOtpRecord.code, code)
        assertEquals(savedOtpRecord.used, used)
        assertEquals(savedOtpRecord.userId, userId.value)
        assertEquals(savedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = code,
            userId = userId
        )

        val actual = assertDoesNotThrow {
            runBlocking {
                verifyOtpService.execute(request)
            }
        }

        assertEquals(actual, com.rusticfox.fingenius.core.entities.OtpVerificationStatus.CODE_EXPIRED)
    }

    @Test
    fun `should successfully verify otp code for user id that was initially generated`() {
        val currentTime = Clock.System.now()

        val code = generateRandomString(6)
        val userCode = generateRandomString(12)
        val userId = com.rusticfox.fingenius.core.entities.UserId(value = userCode)
        val expiryTime = currentTime.plus(1.days).toLocalDateTime(TimeZone.currentSystemDefault())
        val used = false
        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
            code = code,
            userId = userId,
            expiryTime = expiryTime,
            used = used
        )

        // insert an existing record
        val savedOtpRecord = runBlocking {
            assertDoesNotThrow {
                partnerRepository.insert(otpCode)
            }
        }

        assertNotNull(savedOtpRecord)
        assertEquals(savedOtpRecord.code, code)
        assertEquals(savedOtpRecord.used, used)
        assertEquals(savedOtpRecord.userId, userId.value)
        assertEquals(savedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())

        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = code,
            userId = userId
        )

        val actual = assertDoesNotThrow {
            runBlocking {
                verifyOtpService.execute(request)
            }
        }

        assertEquals(actual, com.rusticfox.fingenius.core.entities.OtpVerificationStatus.VERIFIED)

        // check that we actually updated the OTP record
        val updatedOtpRecord = transaction { InvoiceItemModel.find { OtpTable.code eq code }.firstOrNull() }

        assertNotNull(updatedOtpRecord)
        assertEquals(updatedOtpRecord.code, code)
        assertEquals(updatedOtpRecord.used, true)
        assertEquals(updatedOtpRecord.userId, userId.value)
        assertEquals(updatedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())
    }

    @Test
    fun `should throw NotFoundException if OTP can not be found`() {
        val request = com.rusticfox.fingenius.core.entities.VerifyOtpCode(
            otpCode = "123456",
            userId = com.rusticfox.fingenius.core.entities.UserId("09876")
        )

        assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
            runBlocking {
                verifyOtpService.execute(request)
            }
        }
    }
}
