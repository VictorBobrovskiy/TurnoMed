package com.digsol.turnomed.mapper

import com.digsol.turnomed.model.Address
import com.digsol.turnomed.model.Users
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component(value = "addressesMapper")
interface AddressesMapper {

    fun deleteByPrimaryKey(id: Integer): Int

    fun insert(record: Address): Int

    fun insertSelective(record: Address): Int

    fun selectByPrimaryKey(id: Integer): Address

    fun updateByPrimaryKeySelective(record: Address): Int

    fun updateByPrimaryKey(record: Address): Int

    fun selectAll(): List<Address>

    fun getAddressInformation(addressId: Integer): Address

    fun getAddressInformationByEmail(users: Users): Address

    fun detailEventsInfoByAddress(request: SearchEntity): List<EventsSchedule>
}
