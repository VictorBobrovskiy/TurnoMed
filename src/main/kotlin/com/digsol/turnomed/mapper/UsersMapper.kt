package com.digsol.turnomed.mapper

import com.digsol.turnomed.model.Users
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface UsersMapper {
    fun deleteByPrimaryKey(id: Integer): Int

    fun insert(record: Users): Int

    fun insertSelective(record: Users): Int

    fun selectByPrimaryKey(id: Integer): Users

    fun updateByPrimaryKeySelective(record: Users): Int

    fun updateByPrimaryKey(record: Users): Int

    fun listAllUsers(): List<Users>

    fun getUserByEmail(email: String): Users
}
