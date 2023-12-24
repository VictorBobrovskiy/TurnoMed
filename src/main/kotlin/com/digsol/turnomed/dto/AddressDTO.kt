package com.digsol.turnomed.dto

import com.google.gson.JsonObject
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Data

@Data
@ApiModel(description = "Address")
data class AddressDTO(
        @ApiModelProperty(notes = "Address id")
        var id: Integer? = null,

        @ApiModelProperty(notes = "City name")
        var city: String? = null,

        @ApiModelProperty(notes = "Address")
        var address: String? = null,

        @ApiModelProperty(notes = "Clinic name")
        var clinicName: String? = null,

        @ApiModelProperty(notes = "Doctor types")
        var doctorTypes: List<Integer>? = null,

        @ApiModelProperty(notes = "Additional information")
        var info: JsonObject? = null
) : BaseDTO
