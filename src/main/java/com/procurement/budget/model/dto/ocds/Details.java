package com.procurement.budget.model.dto.ocds;

import com.fasterxml.jackson.annotation.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "typeOfBuyer",
        "mainGeneralActivity",
        "mainSectoralActivity",
        "isACentralPurchasingBody",
        "NUTSCode",
        "scale"
})
public class Details {
    @JsonProperty("typeOfBuyer")
    private final TypeOfBuyer typeOfBuyer;

    @JsonProperty("mainGeneralActivity")
    private final MainGeneralActivity mainGeneralActivity;

    @JsonProperty("mainSectoralActivity")
    private final MainSectoralActivity mainSectoralActivity;

    @JsonProperty("isACentralPurchasingBody")
    private final Boolean isACentralPurchasingBody;

    @JsonProperty("NUTSCode")
    private final String nutsCode;

    @JsonProperty("scale")
    private final Scale scale;

    @JsonCreator
    public Details(@JsonProperty("typeOfBuyer") final TypeOfBuyer typeOfBuyer,
                   @JsonProperty("mainGeneralActivity") final MainGeneralActivity mainGeneralActivity,
                   @JsonProperty("mainSectoralActivity") final MainSectoralActivity mainSectoralActivity,
                   @JsonProperty("isACentralPurchasingBody") final Boolean isACentralPurchasingBody,
                   @JsonProperty("NUTSCode") final String nutsCode,
                   @JsonProperty("scale") final Scale scale) {
        this.typeOfBuyer = typeOfBuyer;
        this.mainGeneralActivity = mainGeneralActivity;
        this.mainSectoralActivity = mainSectoralActivity;
        this.isACentralPurchasingBody = isACentralPurchasingBody;
        this.nutsCode = nutsCode;
        this.scale = scale;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(typeOfBuyer)
                .append(mainGeneralActivity)
                .append(mainSectoralActivity)
                .append(isACentralPurchasingBody)
                .append(nutsCode)
                .append(scale)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Details)) {
            return false;
        }
        final Details rhs = (Details) other;
        return new EqualsBuilder().append(typeOfBuyer, rhs.typeOfBuyer)
                .append(mainGeneralActivity, rhs.mainGeneralActivity)
                .append(mainSectoralActivity, rhs.mainSectoralActivity)
                .append(isACentralPurchasingBody, rhs.isACentralPurchasingBody)
                .append(nutsCode, rhs.nutsCode)
                .append(scale, rhs.scale)
                .isEquals();
    }

    public enum MainGeneralActivity {
        DEFENCE("DEFENCE"),
        ECONOMIC_AND_FINANCIAL_AFFAIRS("ECONOMIC_AND_FINANCIAL_AFFAIRS"),
        EDUCATION("EDUCATION"),
        ENVIRONMENT("ENVIRONMENT"),
        GENERAL_PUBLIC_SERVICES("GENERAL_PUBLIC_SERVICES"),
        HEALTH("HEALTH"),
        HOUSING_AND_COMMUNITY_AMENITIES("HOUSING_AND_COMMUNITY_AMENITIES"),
        PUBLIC_ORDER_AND_SAFETY("PUBLIC_ORDER_AND_SAFETY"),
        RECREATION_CULTURE_AND_RELIGION("RECREATION_CULTURE_AND_RELIGION"),
        SOCIAL_PROTECTION("SOCIAL_PROTECTION");
        private final static Map<String, MainGeneralActivity> CONSTANTS = new HashMap<>();

        static {
            for (final MainGeneralActivity c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        MainGeneralActivity(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static MainGeneralActivity fromValue(final String value) {
            final MainGeneralActivity constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(
                        "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

    }

    public enum MainSectoralActivity {
        AIRPORT_RELATED_ACTIVITIES("AIRPORT_RELATED_ACTIVITIES"),
        ELECTRICITY("ELECTRICITY"),
        EXPLORATION_EXTRACTION_COAL_OTHER_SOLID_FUEL("EXPLORATION_EXTRACTION_COAL_OTHER_SOLID_FUEL"),
        EXPLORATION_EXTRACTION_GAS_OIL("EXPLORATION_EXTRACTION_GAS_OIL"),
        PORT_RELATED_ACTIVITIES("PORT_RELATED_ACTIVITIES"),
        POSTAL_SERVICES("POSTAL_SERVICES"),
        PRODUCTION_TRANSPORT_DISTRIBUTION_GAS_HEAT("PRODUCTION_TRANSPORT_DISTRIBUTION_GAS_HEAT"),
        RAILWAY_SERVICES("RAILWAY_SERVICES"),
        URBAN_RAILWAY_TRAMWAY_TROLLEYBUS_BUS_SERVICES("URBAN_RAILWAY_TRAMWAY_TROLLEYBUS_BUS_SERVICES"),
        WATER("WATER");
        private final static Map<String, MainSectoralActivity> CONSTANTS = new HashMap<>();

        static {
            for (final MainSectoralActivity c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        MainSectoralActivity(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static MainSectoralActivity fromValue(final String value) {
            final MainSectoralActivity constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }
    }

    public enum Scale {
        MICRO("micro"),
        SME("sme"),
        LARGE("large"),
        EMPTY("");
        private final static Map<String, Scale> CONSTANTS = new HashMap<>();

        static {
            for (final Details.Scale c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        Scale(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static Scale fromValue(final String value) {
            final Details.Scale constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(
                        "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }
    }

    public enum TypeOfBuyer {
        BODY_PUBLIC("BODY_PUBLIC"),
        EU_INSTITUTION("EU_INSTITUTION"),
        MINISTRY("MINISTRY"),
        NATIONAL_AGENCY("NATIONAL_AGENCY"),
        REGIONAL_AGENCY("REGIONAL_AGENCY"),
        REGIONAL_AUTHORITY("REGIONAL_AUTHORITY");
        private final static Map<String, TypeOfBuyer> CONSTANTS = new HashMap<>();

        static {
            for (final Details.TypeOfBuyer c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TypeOfBuyer(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static TypeOfBuyer fromValue(final String value) {
            final TypeOfBuyer constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            }
            return constant;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

    }
}