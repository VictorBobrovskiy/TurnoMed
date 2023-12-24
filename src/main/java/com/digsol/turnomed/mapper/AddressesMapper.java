package com.digsol.turnomed.mapper;

import com.digsol.turnomed.model.Address;
import com.digsol.turnomed.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


import java.util.List;

@Mapper
@Component
public interface AddressesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Address> selectAll();

    Address getAddressInformation(Integer addressId);

    Address getAddressInformationByEmail(Users users);

   // List<EventsSchedule> detailEventsInfoByAddress(SearchEntity request);
}