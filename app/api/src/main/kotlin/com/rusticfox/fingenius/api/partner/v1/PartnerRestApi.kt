package com.rusticfox.fingenius.api.partner.v1

import com.rusticfox.fingenius.api.partner.PartnerService
import com.rusticfox.fingenius.api.dto.ApiResult
import com.rusticfox.fingenius.api.dto.CreatePartnerRequestDto
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
            runCatching { partnerService.createPartner(payload) }
                .onSuccess { call.respond(message = it, status = HttpStatusCode.Created) }
                .onFailure {
                    call.respond(
                        message = ApiResult(message = it.message ?: "Failed to create OTP"),
                        status = HttpStatusCode.InternalServerError
                    )
                }
        }
    }
}
