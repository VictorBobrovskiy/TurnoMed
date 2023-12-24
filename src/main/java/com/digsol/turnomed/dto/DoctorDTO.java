package com.digsol.turnomed.dto;

import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Doctor")
public class DoctorDTO implements BaseDTO {

    @ApiModelProperty(notes = "Doctor's id")
    private Integer id;

    @ApiModelProperty(notes = "Information")
    private JsonObject info;

    @ApiModelProperty(notes = "Content URL")
    private String contentUrl;

    @ApiModelProperty(notes = "Phone")
    private String phone;

    @ApiModelProperty(notes = "User link")
    private UsersDTO user;
}
