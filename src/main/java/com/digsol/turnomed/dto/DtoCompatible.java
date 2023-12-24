package com.digsol.turnomed.dto;

public interface DtoCompatible<T extends BaseDTO>  {

    T toDto();

}
