package com.rusticfox.fingenius.datastore.partner

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.rusticfox.fingenius.datastore.BaseDataStoreAdapterIntegrationTest
import com.rusticfox.fingenius.testfixtures.data.testPartner
import com.rusticfox.fingenius.testfixtures.utils.TEST_TYPE_INTEGRATION
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.component.inject
import org.koin.dsl.module
import org.koin.test.junit5.KoinTestExtension
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Tag(TEST_TYPE_INTEGRATION)
class PartnerWriteDatastoreIntegrationTest : BaseDataStoreAdapterIntegrationTest() {

    private val partnerDatastoreAdapter by inject<PartnerDataStoreAdapter>()
    private val partnerRepository by inject<PartnerRepository>()
    private val mongoDatabaseCollection: MongoCollection<PartnerModel> by lazy {
        mongoDatabase.getCollection<PartnerModel>("partners")
    }

    @BeforeEach
    fun setUp() {
        super.setup()
        runBlocking {
            run { mongoDatabase.createCollection("partners") }
        }
    }

    @AfterEach
    fun tearDown() {

    }

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single { mongoDatabase }
                single { PartnerRepository(get()) }
                single { PartnerWriteDataStoreAdapter(get()) }
            })
    }

    @Test
    fun `context is created for the test`() {
        assertNotNull(mongoDatabase)
        assertNotNull(partnerRepository)
        assertNotNull(partnerDatastoreAdapter)
    }

    @Test
    fun `should create a partner record in the database`() = runTest {
        val filters = Filters.and(Filters.eq("identifier", testPartner.partnerId.value))

        runBlocking {
            assertDoesNotThrow {
                partnerDatastoreAdapter.create(testPartner)
            }
        }

        mongoDatabaseCollection.find(filters).collect {
            assertNotNull(it)
            assertEquals(it.type, testPartner.type)
        }

    }

//    @Test
//    fun `should throw exception when attempting to create a duplicate OTP record in the database`() {
//        val code = generateRandomString(6)
//        val user = generateRandomString(10)
//        val userId = com.rusticfox.fingenius.core.entities.UserId(user)
//        val expiryTime = LocalDateTime(2023, 1, 1, 1, 1)
//        val used = false
//        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code,
//            userId = userId,
//            expiryTime = expiryTime,
//            used = used
//        )
//
//        // insert an existing record
//        val savedOtpRecord = runBlocking {
//            assertDoesNotThrow {
//                PartnerRepository.insert(otpCode)
//            }
//        }
//        assertNotNull(savedOtpRecord)
//        assertEquals(savedOtpRecord.code, code)
//        assertEquals(savedOtpRecord.used, used)
//        assertEquals(savedOtpRecord.userId, userId.value)
//        assertEquals(savedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())
//
//        runBlocking {
//            assertThrows<com.rusticfox.fingenius.core.exceptions.DatabaseException> {
//                partnerDatastoreAdapter.create(otpCode)
//            }
//        }
//    }
//
//    @Test
//    fun `should be able to mark an existing OTP record as used`() {
//        val code = generateRandomString(6)
//        val user = generateRandomString(12)
//        val userId = com.rusticfox.fingenius.core.entities.UserId(user)
//        val expiryTime = LocalDateTime(2023, 1, 1, 1, 1)
//        val used = false
//        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code,
//            userId = userId,
//            expiryTime = expiryTime,
//            used = used
//        )
//
//        // insert an existing record
//        val savedOtpRecord = runBlocking {
//            assertDoesNotThrow {
//                PartnerRepository.insert(otpCode)
//            }
//        }
//        assertNotNull(savedOtpRecord)
//        assertEquals(savedOtpRecord.code, code)
//        assertEquals(savedOtpRecord.used, used)
//        assertEquals(savedOtpRecord.userId, userId.value)
//        assertEquals(savedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())
//
//        // mark the OTP as used
//        runBlocking {
//            assertDoesNotThrow {
//                partnerDatastoreAdapter.markOtpAsUsed(otpCode)
//            }
//        }
//
//        // check that we actually updated the OTP record
//        val actual = transaction { InvoiceItemModel.find { OtpTable.code eq code }.firstOrNull() }
//
//        assertNotNull(actual)
//        assertEquals(actual.code, code)
//        assertEquals(actual.used, true)
//        assertEquals(actual.userId, userId.value)
//        assertEquals(actual.expiryTime, expiryTime.toJavaLocalDateTime())
//    }
//
//    @Test
//    fun `should throw an exception when attempting to mark a non existent OTP code as used`() {
//        val code = generateRandomString(11)
//        val user = generateRandomString(12)
//        val userId = com.rusticfox.fingenius.core.entities.UserId(user)
//        val expiryTime = LocalDateTime(2023, 1, 1, 1, 1)
//        val used = false
//        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code,
//            userId = userId,
//            expiryTime = expiryTime,
//            used = used
//        )
//
//        runBlocking {
//            assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
//                partnerDatastoreAdapter.markOtpAsUsed(otpCode)
//            }
//        }
//    }
//
//    @Test
//    fun `should be able to retrieve an existing OTP record`() {
//        val code = generateRandomString()
//        val user = generateRandomString(5)
//        val userId = com.rusticfox.fingenius.core.entities.UserId(user)
//        val expiryTime = LocalDateTime(2023, 1, 1, 1, 1)
//        val used = false
//        val otpCode = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code,
//            userId = userId,
//            expiryTime = expiryTime,
//            used = used
//        )
//
//        // insert an existing record
//        val savedOtpRecord = runBlocking {
//            assertDoesNotThrow {
//                PartnerRepository.insert(otpCode)
//            }
//        }
//        assertNotNull(savedOtpRecord)
//        assertEquals(savedOtpRecord.code, code)
//        assertEquals(savedOtpRecord.used, used)
//        assertEquals(savedOtpRecord.userId, userId.value)
//        assertEquals(savedOtpRecord.expiryTime, expiryTime.toJavaLocalDateTime())
//
//        val actual = runBlocking {
//            assertDoesNotThrow {
//                partnerDatastoreAdapter.getOtpCode(code)
//            }
//        }
//
//        assertNotNull(actual)
//        assertEquals(actual.code, code)
//        assertEquals(actual.used, used)
//        assertEquals(actual.userId, userId)
//        assertEquals(actual.expiryTime, expiryTime)
//    }
//
//    @Test
//    fun `should throw NotFoundException when retrieving a non-existing OTP record`() {
//        val code = generateRandomString(12)
//
//        runBlocking {
//            assertThrows<com.rusticfox.fingenius.core.exceptions.NotFoundException> {
//                partnerDatastoreAdapter.getOtpCode(code)
//            }
//        }
//    }
//
//    @Test
//    fun `should be able to retrieve all existing OTP records`() {
//        val code1 = generateRandomString()
//        val code2 = generateRandomString()
//        val user = generateRandomString(10)
//        val userId = com.rusticfox.fingenius.core.entities.UserId(user)
//        val expiryTime1 = LocalDateTime(2023, 1, 1, 1, 1)
//        val expiryTime2 = LocalDateTime(2023, 2, 1, 1, 1)
//        val used1 = true
//        val used2 = false
//        val otpCode1 = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code1,
//            userId = userId,
//            expiryTime = expiryTime1,
//            used = used1
//        )
//        val otpCode2 = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code2,
//            userId = userId,
//            expiryTime = expiryTime2,
//            used = used2
//        )
//
//        // insert record(s)
//        runBlocking {
//            assertDoesNotThrow {
//                PartnerRepository.insert(otpCode1)
//                PartnerRepository.insert(otpCode2)
//            }
//        }
//
//        val actual = runBlocking {
//            assertDoesNotThrow {
//                partnerDatastoreAdapter.getAll()
//            }
//        }
//
//        assertNotNull(actual)
//        assertEquals(2, actual.size)
//
//        val actualCodeMap = actual.associateBy { it.code }
//
//        val actualOtpCode1 = actualCodeMap[code1]
//        assertNotNull(actualOtpCode1)
//        assertEquals(actualOtpCode1.code, code1)
//        assertEquals(actualOtpCode1.used, used1)
//        assertEquals(actualOtpCode1.userId, userId)
//        assertEquals(actualOtpCode1.expiryTime, expiryTime1)
//
//        val actualOtpCode2 = actualCodeMap[code2]
//        assertNotNull(actualOtpCode2)
//        assertEquals(actualOtpCode2.code, code2)
//        assertEquals(actualOtpCode2.used, used2)
//        assertEquals(actualOtpCode2.userId, userId)
//        assertEquals(actualOtpCode2.expiryTime, expiryTime2)
//    }
//
//    @Test
//    fun `should be able to retrieve all existing OTP records for a given user`() {
//        val code1 = generateRandomString()
//        val code2 = generateRandomString()
//        val user1 = generateRandomString(10)
//        val user2 = generateRandomString(10)
//        val userId1 = com.rusticfox.fingenius.core.entities.UserId(user1)
//        val userId2 = com.rusticfox.fingenius.core.entities.UserId(user2)
//        val expiryTime1 = LocalDateTime(2023, 1, 1, 1, 1)
//        val expiryTime2 = LocalDateTime(2023, 2, 1, 1, 1)
//        val used1 = true
//        val used2 = false
//        val otpCode1 = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code1,
//            userId = userId1,
//            expiryTime = expiryTime1,
//            used = used1
//        )
//        val otpCode2 = com.rusticfox.fingenius.core.entities.OtpCode(
//            code = code2,
//            userId = userId2,
//            expiryTime = expiryTime2,
//            used = used2
//        )
//
//        // insert record(s)
//        runBlocking {
//            assertDoesNotThrow {
//                PartnerRepository.insert(otpCode1)
//                PartnerRepository.insert(otpCode2)
//            }
//        }
//
//        val actualUser1 = runBlocking {
//            assertDoesNotThrow {
//                partnerDatastoreAdapter.getAllByUserId(userId1)
//            }
//        }
//
//        assertNotNull(actualUser1)
//        assertEquals(1, actualUser1.size)
//
//        val actualCodeMap1 = actualUser1.associateBy { it.code }
//        val actualOtpCode1 = actualCodeMap1[code1]
//
//        assertNotNull(actualOtpCode1)
//        assertEquals(actualOtpCode1.code, code1)
//        assertEquals(actualOtpCode1.used, used1)
//        assertEquals(actualOtpCode1.userId, userId1)
//        assertEquals(actualOtpCode1.expiryTime, expiryTime1)
//
//        val actualUser2 = runBlocking {
//            assertDoesNotThrow {
//                partnerDatastoreAdapter.getAllByUserId(userId2)
//            }
//        }
//
//        assertNotNull(actualUser2)
//        assertEquals(1, actualUser2.size)
//
//        val actualCodeMap2 = actualUser2.associateBy { it.code }
//
//        val actualOtpCode2 = actualCodeMap2[code2]
//
//        assertNotNull(actualOtpCode2)
//        assertEquals(actualOtpCode2.code, code2)
//        assertEquals(actualOtpCode2.used, used2)
//        assertEquals(actualOtpCode2.userId, userId2)
//        assertEquals(actualOtpCode2.expiryTime, expiryTime2)
//    }
}
