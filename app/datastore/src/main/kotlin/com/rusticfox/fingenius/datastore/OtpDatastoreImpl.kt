package com.rusticfox.fingenius.datastore

import com.rusticfox.fingenius.core.entities.OtpCode
import com.rusticfox.fingenius.core.entities.UserId
import com.rusticfox.fingenius.core.exceptions.DatabaseException
import com.rusticfox.fingenius.core.exceptions.NotFoundException
import com.rusticfox.fingenius.core.ports.OtpDataStore

class OtpDatastoreImpl(private val otpRepository: OtpRepository) : com.rusticfox.fingenius.core.ports.OtpDataStore {

    override suspend fun create(otpCode: com.rusticfox.fingenius.core.entities.OtpCode): com.rusticfox.fingenius.core.entities.OtpCode = runCatching {
        OtpRepository.insert(otpCode)
        otpCode
    }
        .getOrElse { throw com.rusticfox.fingenius.core.exceptions.DatabaseException(
            "Failed to create OTP code $otpCode",
            it
        )
        }

    override suspend fun markOtpAsUsed(otpCode: com.rusticfox.fingenius.core.entities.OtpCode) {
        OtpRepository.findByCode(otpCode.code) ?: throw com.rusticfox.fingenius.core.exceptions.NotFoundException("Otp ${otpCode.code} not found")

        runCatching { OtpRepository.markAsUsed(otpCode.code) }
            .getOrElse { throw com.rusticfox.fingenius.core.exceptions.DatabaseException(
                "Failed to mark OTP $otpCode as used",
                it
            )
            }
    }

    override suspend fun getOtpCode(code: String): com.rusticfox.fingenius.core.entities.OtpCode {
        val otpEntity = OtpRepository.findByCode(code) ?: throw com.rusticfox.fingenius.core.exceptions.NotFoundException(
            "Otp $code not found"
        )
        return mapModelToEntity(otpEntity)
    }

    override suspend fun getAll(): Collection<com.rusticfox.fingenius.core.entities.OtpCode> = runCatching { OtpRepository.findAll() }
        .mapCatching { it.map(::mapModelToEntity) }
        .getOrElse { throw com.rusticfox.fingenius.core.exceptions.DatabaseException(
            "Failed to get all OTP codes",
            it
        )
        }

    override suspend fun getAllByUserId(userId: com.rusticfox.fingenius.core.entities.UserId): Collection<com.rusticfox.fingenius.core.entities.OtpCode> = runCatching {
        OtpRepository.findAllByUserId(userId.value)
    }
        .mapCatching { it.map(::mapModelToEntity) }
        .getOrElse { throw com.rusticfox.fingenius.core.exceptions.DatabaseException(
            "Failed to get all OTP codes",
            it
        )
        }
}
