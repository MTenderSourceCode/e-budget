package com.procurement.budget.model.dto.check

import com.fasterxml.jackson.annotation.JsonCreator

data class CheckSourcePartyDto @JsonCreator constructor(

        val id: String?,

        val name: String?
)
