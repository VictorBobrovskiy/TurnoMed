package com.digsol.turnomed.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@ApiModel(description = "Users")
public class UsersDTO implements BaseDTO{

    @ApiModelProperty(notes = "Name")
    private String firstName;

    @ApiModelProperty(notes = "Surname")
    private String lastName;

    @ApiModelProperty(notes = "Email")
    private String email;

    @ApiModelProperty(notes = "Password")
    private String password;

    @ApiModelProperty(notes = "Session token")
    private String token;

    @ApiModelProperty(notes = "Registration Status")
    private Integer regStatus = 0;

    @ApiModelProperty(notes = "Registration date")
    private Timestamp regDate;

    @ApiModelProperty(notes = "Registration confirmatiom date")
    private Timestamp confirmDate;

    public UsersDTO() {
    }

    public UsersDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UsersDTO(String firstName, String lastName, String email, String password, Integer regStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.regStatus = regStatus;
    }

    public UsersDTO(String firstName, String middleName, String lastName, String email, String password, String token, Integer regStatus, Timestamp regDate, Timestamp confirmDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
        this.regStatus = regStatus;
        this.regDate = regDate;
        this.confirmDate = confirmDate;
    }
}
