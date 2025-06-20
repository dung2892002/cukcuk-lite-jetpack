package com.example.domain.model

import com.example.domain.enums.DomainError

data class ResponseData<T> (
    var isSuccess: Boolean = true,
    var error: DomainError? = null,
    var objectData: T? = null
)