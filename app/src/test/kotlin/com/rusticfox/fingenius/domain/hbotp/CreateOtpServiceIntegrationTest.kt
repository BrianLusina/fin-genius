package com.rusticfox.fingenius.domain.hbotp

import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.ports.OtpDataStore
import com.rusticfox.fingenius.core.services.OtpCodeGenerator
import com.rusticfox.fingenius.datastore.OtpDatastoreImpl
import com.rusticfox.fingenius.datastore.OtpRepository
import com.rusticfox.fingenius.datastore.models.OtpEntity
import com.rusticfox.fingenius.datastore.models.OtpTable
import com.sanctumlabs.otp.domain.BaseIntegrationTest
import com.sanctumlabs.otp.domain.generators.HmacAlgorithms
import com.rusticfox.fingenius.domain.generators.HmacBasedCodeGenerator
import com.rusticfox.fingenius.domain.generators.HmacCodeGeneratorConfig
import com.sanctumlabs.otp.domain.services.CreateOtpServiceImpl
import com.sanctumlabs.otp.testfixtures.utils.generateRandomString
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.component.inject
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Tag("integration")
class CreateOtpServiceIntegrationTest : BaseIntegrationTest(), KoinTest {
    private val key = generateRandomString()
    private val codeDigits = 6
    private val hmacAlgorithm = HmacAlgorithms.SHA1
    private val hmacBasedOtpCodeGenConfig = com.rusticfox.fingenius.domain.generators.HmacCodeGeneratorConfig(
        codeDigits = codeDigits,
        hmacAlgorithm = hmacAlgorithm
    )
    private val otpDatastore by inject<com.rusticfox.fingenius.core.ports.OtpDataStore>()
    private val otpRepository by inject<OtpRepository>()
    private val otpGenerator by inject<com.rusticfox.fingenius.core.services.OtpCodeGenerator>()
    private val createOtpService by inject<CreateOtpServiceImpl>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single { OtpRepository }
                single<com.rusticfox.fingenius.core.ports.OtpDataStore> { OtpDatastoreImpl(get()) }
                single<com.rusticfox.fingenius.core.services.OtpCodeGenerator> {
                    com.rusticfox.fingenius.domain.generators.HmacBasedCodeGenerator(
                        key,
                        hmacBasedOtpCodeGenConfig
                    )
                }
                single { CreateOtpServiceImpl(get(), get()) }
            })
    }

    @Test
    fun `context is created for the test`() {
        assertNotNull(otpRepository)
        assertNotNull(otpDatastore)
        assertNotNull(otpGenerator)
        assertNotNull(createOtpService)
    }

    @Test
    fun `should successfully generate otp code for user id`() {
        val user = generateRandomString()
        val userId = com.rusticfox.fingenius.core.entities.UserId(user)

        val otpCode = assertDoesNotThrow {
            runBlocking {
                createOtpService.execute(userId)
            }
        }

        val actual = transaction { OtpEntity.find { OtpTable.userId eq user }.firstOrNull() }

        assertNotNull(actual)
        assertEquals(actual.code.length, codeDigits)
        assertEquals(actual.used, false)
        assertEquals(actual.userId, userId.value)

        assertEquals(otpCode.code.length, codeDigits)
        assertEquals(otpCode.used, actual.used)
        assertEquals(otpCode.userId, userId)
        assertEquals(otpCode.expiryTime, actual.expiryTime.toKotlinLocalDateTime())
    }
}