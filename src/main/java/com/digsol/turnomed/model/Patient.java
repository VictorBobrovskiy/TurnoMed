package com.digsol.turnomed.model;

import com.digsol.turnomed.dto.DtoCompatible;
import lombok.Data;

import java.sql.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Data
public class Patient  {

    private Integer userId;

    private String info;

    private String phone;

    private Date dateOfBirth;

    private Boolean subscribe;

    private String sex;

    private String foto;

    public Boolean isEmpty() {
        return Stream.of(info, phone, dateOfBirth, subscribe, sex).allMatch(Objects::isNull);
    }
}