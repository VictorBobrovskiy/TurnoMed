package com.digsol.turnomed.dto

interface DtoCompatible<T : BaseDTO> {
    fun toDto(): T
}
