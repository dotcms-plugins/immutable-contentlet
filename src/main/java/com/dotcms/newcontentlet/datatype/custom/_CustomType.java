package com.dotcms.newcontentlet.datatype.custom;

import com.dotcms.newcontentlet.datatype.annotations.ValueType;


import com.dotcms.newcontentlet.datatype.base.BaseTypes._TextType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@ValueType
@Immutable
@JsonDeserialize(as = CustomType.class)
@JsonTypeName(value = _CustomType.TYPENAME)
public interface  _CustomType extends _TextType {
    String TYPENAME = "custom-type";
    @Override
    default String type() {
        return TYPENAME;
    };

    Optional<String> customFieldData();
}
