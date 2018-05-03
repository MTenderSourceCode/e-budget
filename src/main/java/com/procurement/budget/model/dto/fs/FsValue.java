package com.procurement.budget.model.dto.fs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.procurement.budget.model.dto.databinding.MoneyDeserializer;
import com.procurement.budget.model.dto.ocds.Currency;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "currency"
})
public class FsValue {

    @NotNull
    @JsonProperty("amount")
    @JsonDeserialize(using = MoneyDeserializer.class)
    private final BigDecimal amount;

    @NotNull
    @JsonProperty("currency")
    private final Currency currency;

    @JsonCreator
    public FsValue(@JsonProperty("amount") final BigDecimal amount,
                   @JsonProperty("currency") final Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(amount)
                .append(currency)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FsValue)) {
            return false;
        }
        final FsValue rhs = (FsValue) other;
        return new EqualsBuilder().append(amount, rhs.amount)
                .append(currency, rhs.currency)
                .isEquals();
    }
}
