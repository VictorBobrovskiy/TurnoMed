package com.digsol.turnomed.mapper

import com.digsol.turnomed.model.Patient
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component(value = "patientMapper")
interface PatientMapper {
    fun deleteByPrimaryKey(userId: Integer): Int

    fun insert(record: Patient): Int

    fun insertSelective(record: Patient): Int

    fun selectByPrimaryKey(userId: Integer): Patient

    fun updateByPrimaryKeySelective(record: Patient): Int

    fun updateByPrimaryKey(record: Patient): Int
}
