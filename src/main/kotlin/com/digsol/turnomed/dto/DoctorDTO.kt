package com.digsol.turnomed.dto

import com.google.gson.JsonObject
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@ApiModel(description = "Doctor")
data class DoctorDTO(
    @ApiModelProperty(notes = "Doctor's id")
    var id: Integer? = null,

    @ApiModelProperty(notes = "Information")
    var info: JsonObject? = null,

    @ApiModelProperty(notes = "Content URL")
    var contentUrl: String? = null,

    @ApiModelProperty(notes = "Phone")
    var phone: String? = null,

    @ApiModelProperty(notes = "User link")
    var user: UsersDTO? = null
) : BaseDTO
