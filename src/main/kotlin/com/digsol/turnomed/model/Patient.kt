package com.digsol.turnomed.model

import com.digsol.turnomed.dto.DtoCompatible
import java.sql.Date
import java.util.Objects
import java.util.stream.Stream

data class Patient(
    var userId: Integer? = null,
    var info: String? = null,
    var phone: String? = null,
    var dateOfBirth: Date? = null,
    var subscribe: Boolean? = null,
    var sex: String? = null,
    var foto: String? = null
) {

    fun isEmpty(): Boolean {
        return Stream.of(info, phone, dateOfBirth, subscribe, sex).allMatch(Objects::isNull)
    }
}
