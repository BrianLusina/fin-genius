package com.rusticfox.fingenius.api.partner.v1

import com.rusticfox.fingenius.api.constructPageRequestFromQueryParameters
import com.rusticfox.fingenius.api.partner.PartnerService
import com.rusticfox.fingenius.api.dto.ApiResult
import com.rusticfox.fingenius.api.partner.dto.PartnerDto
import com.rusticfox.fingenius.api.partner.resources.PartnerResource
import com.rusticfox.fingenius.core.ports.datastore.dto.PageRequestSort
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDateTime
import org.koin.ktor.ext.inject

fun Route.partnerV1ApiRoutes() {
    val partnerService: PartnerService by inject()

    route("/api/v1/partner") {
        post<PartnerDto>("/") { payload ->
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

        put<PartnerDto> ("/{id}") { payload ->
            val partnerId = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing partner ID")

            runCatching { partnerService.updatePartner(partnerId, payload) }
                .onSuccess {
                    call.respond(
                        message = ApiResult(
                            status = HttpStatusCode.Created.value,
                            data = it,
                            message = "Partner successfully updated"
                        ),
                        status = HttpStatusCode.OK,
                    )
                }
                .onFailure {
                    call.respond(
                        message = ApiResult(
                            status = HttpStatusCode.InternalServerError.value,
                            message = it.message ?: "Failed to update partner"
                        ),
                        status = HttpStatusCode.InternalServerError
                    )
                }
        }

        get {
            runCatching {
                val partnerId = call.parameters["id"]
                val partnerType = call.request.queryParameters["type"]
                val partnerStatus = call.request.queryParameters["status"]

                val pageRequest = constructPageRequestFromQueryParameters(call.request.queryParameters)

                when {
                    partnerId != null -> {
                        partnerService.getPartnerById(partnerId)
                    }
                    else -> {
                        partnerService.getAllPartners(partnerType, partnerStatus, pageRequest)
                    }
                }
            }
            .onSuccess {
                call.respond(
                    message = ApiResult(
                        status = HttpStatusCode.OK.value,
                        data = it,
                        message = "Successfully retrieved"
                    ),
                    status = HttpStatusCode.OK,
                )
            }
            .onFailure {
                call.respond(
                    message = ApiResult(
                        status = HttpStatusCode.InternalServerError.value,
                        message = it.message ?: "Failed to retrieve partner"
                    ),
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}
