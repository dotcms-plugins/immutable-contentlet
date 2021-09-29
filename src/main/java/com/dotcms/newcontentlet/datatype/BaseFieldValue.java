package com.dotcms.newcontentlet.datatype;

import com.dotcms.newcontentlet.datatype.annotations.ValueType;
import com.dotcms.newcontentlet.datatype.base.BaseTypes;
import com.dotcms.newcontentlet.datatype.base.BaseTypes._DateTimeType;
import com.dotcms.newcontentlet.datatype.base.BaseTypes._FloatType;
import com.dotcms.newcontentlet.datatype.base.BaseTypes._ListType;
import com.dotcms.newcontentlet.datatype.base.BaseTypes._LongType;
import com.dotcms.newcontentlet.datatype.base.BaseTypes._TextType;
import com.dotcms.newcontentlet.datatype.base.DateTimeType;
import com.dotcms.newcontentlet.datatype.base.FloatType;
import com.dotcms.newcontentlet.datatype.base.ListType;
import com.dotcms.newcontentlet.datatype.base.LongType;
import com.dotcms.newcontentlet.datatype.base.TextType;
import com.dotcms.newcontentlet.datatype.custom.CustomType;
import com.dotcms.newcontentlet.datatype.custom._CustomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Parameter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXISTING_PROPERTY, property = "type")

// Adds coupling to register subtypes here.  Can be changed to scan annotations
// to register subtypes on initialization of ObjectMapper;
@JsonSubTypes({
        @JsonSubTypes.Type(name = BaseTypes.FLOAT_TYPE_TYPENAME, value = FloatType.class),
        @JsonSubTypes.Type(name = BaseTypes.TEXT_TYPE_TYPENAME, value = TextType.class),
        @JsonSubTypes.Type(name = _CustomType.TYPENAME, value = CustomType.class),
        @JsonSubTypes.Type(name = BaseTypes.LIST_TYPE_TYPENAME, value = ListType.class),
        @JsonSubTypes.Type(name = BaseTypes.LONG_TYPE_TYPENAME, value = LongType.class),
        @JsonSubTypes.Type(name = BaseTypes.DATETIME_TYPE_TYPENAME, value = DateTimeType.class)
})
@ValueType
public interface BaseFieldValue<T> {

    @JsonProperty("type")
    default String type() {
       return "unknown";
    };

    @JsonProperty("value")
    @Parameter
    T value();

    @Auxiliary
    @JsonIgnore
    default boolean isString() {
        return value() instanceof String;
    }
    @Auxiliary

    @JsonIgnore
    default boolean isLong() {
        return value() instanceof Long;
    }

    @Auxiliary
    @JsonIgnore
    default boolean isFloat() {
        return value() instanceof Float;
    }

    @Auxiliary
    @JsonIgnore
    default boolean isDateTime() {
        return value() instanceof Instant;
    }


    @Auxiliary
    @JsonIgnore
    default boolean isList() {
        return value() instanceof List;
    }

    @JsonIgnore
    default String stringValue() {
        return value().toString();
    }

    @Auxiliary
    @JsonIgnore
    default Optional<Long> longValue() {
        return Optional.ofNullable(isLong()? (Long)value():null);
    }



    @Auxiliary
    @JsonIgnore
    default Optional<Float> floatValue() {
        return Optional.ofNullable(isFloat()? (Float)value():null);
    }
    @Auxiliary
    @JsonIgnore
    default Optional<Instant> dateTimeValue() {
        return Optional.ofNullable(isDateTime()? (Instant) value():null);
    }



}
