package com.digsol.turnomed.model;

import com.digsol.turnomed.dto.AddressDTO;
import com.digsol.turnomed.dto.DtoCompatible;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

@Data
@Slf4j
public class Address implements DtoCompatible<AddressDTO> {

    private Integer id;

    private String city;

    private String name;

    private String address;

    private List<Integer> sportTypes;

    private String info;

    @Override
    public AddressDTO toDto() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(this.getId());
        addressDTO.setCity(this.getCity());
        addressDTO.setClinicName(this.getName());
        addressDTO.setAddress(this.address);
        addressDTO.setDoctorTypes(this.sportTypes);
        addressDTO.setInfo(this.info != null ? new JsonParser().parse(this.info).getAsJsonObject() : null);
        return addressDTO;
    }
}