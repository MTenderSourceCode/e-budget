package com.procurement.budget.model.dto.check

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.procurement.budget.model.dto.databinding.MoneyDeserializer
import com.procurement.budget.model.dto.fs.OrganizationReferenceFs
import com.procurement.budget.model.dto.ocds.*
import java.math.BigDecimal

data class CheckBsRq @JsonCreator constructor(

        val planning: Planning,

        val buyer: OrganizationReferenceBs
)

data class CheckBsRs @JsonCreator constructor(

        val treasuryBudgetSources: Set<BudgetSource>?,

        val funders: HashSet<OrganizationReferenceFs>?,

        val payers: HashSet<OrganizationReferenceFs>?,

        val buyer: OrganizationReferenceBs?,

        val addedEI: Set<String>?,

        val excludedEI: Set<String>?,

        val addedFS: Set<String>?,

        val excludedFS: Set<String>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Planning @JsonCreator constructor(

        val budget: Budget
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Budget @JsonCreator constructor(

        val budgetAllocation: Set<BudgetAllocation>,

        val budgetSource: Set<BudgetSource>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BudgetAllocation @JsonCreator constructor(

        val budgetBreakdownID: String,

        val period: Period,

        val relatedItem: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BudgetSource @JsonCreator constructor(

        val budgetBreakdownID: String,

        @JsonDeserialize(using = MoneyDeserializer::class)
        val amount: BigDecimal,

        val currency: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrganizationReferenceBs @JsonCreator constructor(

        var id: String?,

        val name: String,

        val identifier: Identifier,

        val address: Address,

        val additionalIdentifiers: HashSet<Identifier>?,

        val contactPoint: ContactPoint?,

        val details: Details?
)