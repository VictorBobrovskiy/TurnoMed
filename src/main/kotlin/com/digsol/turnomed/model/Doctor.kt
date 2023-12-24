package com.digsol.turnomed.model

import com.digsol.turnomed.dto.DoctorDTO
import com.digsol.turnomed.dto.DtoCompatible
import com.digsol.turnomed.dto.UsersDTO
import com.google.gson.JsonParser
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Doctor")
data class Doctor(
    @ApiModelProperty(notes = "Doctor's id")
    var id: Integer? = null,

    @ApiModelProperty(notes = "Information")
    var info: String? = null,

    @ApiModelProperty(notes = "Content URL")
    var contentUrl: String? = null,

    @ApiModelProperty(notes = "Phone")
    var phone: String? = null,

    @ApiModelProperty(notes = "User link")
    var user: UsersDTO? = null
) : DtoCompatible<DoctorDTO> {

    override fun toDto(): DoctorDTO {
        val doctorDTO = DoctorDTO()
        doctorDTO.id = this.id
        doctorDTO.info = this.info?.let { JsonParser().parse(it).asJsonObject }
        doctorDTO.contentUrl = this.contentUrl
        doctorDTO.phone = this.phone
        doctorDTO.user = this.user
        return doctorDTO
    }
}
