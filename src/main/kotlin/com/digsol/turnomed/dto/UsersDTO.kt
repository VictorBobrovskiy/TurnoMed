package com.digsol.turnomed.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import lombok.Getter
import lombok.Setter
import java.sql.Timestamp

@Getter
@Setter
@ApiModel(description = "Users")
data class UsersDTO(

    @ApiModelProperty(notes = "Name")
    var firstName: String? = null,

    @ApiModelProperty(notes = "Surname")
    var lastName: String? = null,

    @ApiModelProperty(notes = "Email")
    var email: String? = null,

    @ApiModelProperty(notes = "Password")
    var password: String? = null,

    @ApiModelProperty(notes = "Session token")
    var token: String? = null,

    @ApiModelProperty(notes = "Registration Status")
    var regStatus: Integer = 0,

    @ApiModelProperty(notes = "Registration date")
    var regDate: Timestamp? = null,

    @ApiModelProperty(notes = "Registration confirmation date")
    var confirmDate: Timestamp? = null
) : BaseDTO {

    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) : this(firstName, lastName, email, password, 0)

    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        regStatus: Int
    ) : this(firstName, lastName, email, password) {
        this.regStatus = regStatus
    }

    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        token: String,
        regStatus: Int,
        regDate: Timestamp,
        confirmDate: Timestamp
    ) : this(firstName, lastName, email, password, regStatus) {
        this.token = token
        this.regDate = regDate
        this.confirmDate = confirmDate
    }
}
