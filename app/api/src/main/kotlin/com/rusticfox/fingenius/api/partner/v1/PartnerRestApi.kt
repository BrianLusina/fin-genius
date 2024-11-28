package com.rusticfox.fingenius.api.partner.v1

import com.rusticfox.fingenius.api.partner.PartnerService
import com.rusticfox.fingenius.api.dto.ApiResult
import com.rusticfox.fingenius.api.partner.dto.CreatePartnerRequestDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.partnerV1ApiRoutes() {
    val partnerService: PartnerService by inject()

    route("/api/v1/partner") {
        post<CreatePartnerRequestDto>("/") { payload ->
            runCatching { partnerService.createPartner(payload) }
                .onSuccess {
                    call.respond(
                        message = ApiResult(
                            status = HttpStatusCode.Created.value,
                            data = it,
                            message = "Partner successfully created"
                        ),
                        status = HttpStatusCode.Created,
                    )
                }
                .onFailure {
                    call.respond(
                        message = ApiResult(
                            status = HttpStatusCode.InternalServerError.value,
                            message = it.message ?: "Failed to create OTP"
                        ),
                        status = HttpStatusCode.InternalServerError
                    )
                }
        }
    }
}
