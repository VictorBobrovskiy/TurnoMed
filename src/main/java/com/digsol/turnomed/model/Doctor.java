package com.digsol.turnomed.model;

import com.digsol.turnomed.dto.*;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Setter
@Getter
@ApiModel(description = "Doctor")
public class Doctor implements DtoCompatible<DoctorDTO> {

    @ApiModelProperty(notes = "Doctor's id")
    private Integer id;

    @ApiModelProperty(notes = "Information")
    private String info;

    @ApiModelProperty(notes = "Content URL")
    private String contentUrl;

    @ApiModelProperty(notes = "Phone")
    private String phone;

    @ApiModelProperty(notes = "User link")
    private UsersDTO user;

    @Override
    public DoctorDTO toDto() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(this.id);
        doctorDTO.setInfo(Optional.ofNullable(this.info).map(o -> new JsonParser().parse(o).getAsJsonObject()).orElse(null));
        doctorDTO.setContentUrl(this.contentUrl);
        doctorDTO.setPhone(this.phone);
        doctorDTO.setUser(this.user);
        return doctorDTO;
    }
}