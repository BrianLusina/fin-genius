package com.rusticfox.fingenius.api.partner.v1

import com.rusticfox.fingenius.api.partner.PartnerService
import com.rusticfox.fingenius.api.dto.ApiResult
import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
import com.rusticfox.fingenius.api.dto.OtpResponseDto
import com.rusticfox.fingenius.api.dto.OtpVerifyDto
import com.rusticfox.fingenius.api.dto.OtpVerifyResponseDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.partnerV1ApiRoutes() {
    val partnerService: PartnerService by inject()

    route("/api/v1/partner/otp") {
        post<CreatePartnerRequestDto> { payload ->
            runCatching {
                partnerService.createPartner(payload)
                OtpResponseDto(
                    userId = payload.userId,
                    code = otpCode.code,
                    expiryTime = otpCode.expiryTime
                )
            }
                .onSuccess { call.respond(message = it, status = HttpStatusCode.Created) }
                .onFailure {
                    call.respond(
                        message = ApiResult(message = it.message ?: "Failed to create OTP"),
                        status = HttpStatusCode.InternalServerError
                    )
                }
        }

        post<OtpVerifyDto>("/verify") { payload ->
            runCatching {
                val status = partnerService.verifyOtp(payload)
                OtpVerifyResponseDto(
                    userId = payload.userId,
                    code = payload.code,
                    status = status
                )
            }
                .onSuccess { call.respond(message = it, status = HttpStatusCode.OK) }
                .onFailure {
                    val status = when (it) {
                        is com.rusticfox.fingenius.core.exceptions.NotFoundException -> HttpStatusCode.NotFound
                        else -> HttpStatusCode.InternalServerError
                    }

                    call.respond(
                        message = ApiResult(message = it.message ?: "Failed to verify OTP"),
                        status = status
                    )
                }
        }
    }
}
