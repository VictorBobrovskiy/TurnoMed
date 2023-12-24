package com.digsol.turnomed.model

import com.digsol.turnomed.dto.AddressDTO
import com.digsol.turnomed.dto.DtoCompatible
import com.google.gson.JsonParser
import lombok.extern.slf4j.Slf4j

@Slf4j
data class Address(
    var id: Integer? = null,
    var city: String? = null,
    var name: String? = null,
    var address: String? = null,
    var sportTypes: List<Integer>? = null,
    var info: String? = null
) : DtoCompatible<AddressDTO> {

    override fun toDto(): AddressDTO {
        val addressDTO = AddressDTO()
        addressDTO.id = this.id
        addressDTO.city = this.city
        addressDTO.clinicName = this.name
        addressDTO.address = this.address
        addressDTO.doctorTypes = this.sportTypes
        addressDTO.info = this.info?.let { JsonParser().parse(it).asJsonObject }
        return addressDTO
    }
}
