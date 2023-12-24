package com.digsol.turnomed.dto;

import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Address")
public class AddressDTO implements BaseDTO {

    @ApiModelProperty(notes = "Address id")
    private Integer id;

    @ApiModelProperty(notes = "City name")
    private String city;

    @ApiModelProperty(notes = "Address")
    private String address;

    @ApiModelProperty(notes = "Clinic name")
    private String clinicName;

    @ApiModelProperty(notes = "Doctor types")
    private List<Integer> doctorTypes;

    @ApiModelProperty(notes = "Additional information")
    private JsonObject info;
}
