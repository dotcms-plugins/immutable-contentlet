package com.dotcms.newcontentlet.datatype.base;

import com.dotcms.newcontentlet.datatype.BaseFieldValue;
import com.dotcms.newcontentlet.datatype.Jsonable;
import com.dotcms.newcontentlet.datatype.annotations.ValueType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.List;
import org.immutables.value.Value.Immutable;

@ValueType
public interface BaseTypes extends Jsonable {

    String TEXT_TYPE_TYPENAME="text";
    String LONG_TYPE_TYPENAME="long";
    String DATETIME_TYPE_TYPENAME="date-time";
    String FLOAT_TYPE_TYPENAME="float";
    String LIST_TYPE_TYPENAME="list-type";

    @Immutable
    interface _TextType extends BaseFieldValue<String> {
        @Override
        default String type() {
            return TEXT_TYPE_TYPENAME;
        };
    }

    @Immutable
    interface _LongType extends BaseFieldValue<Long> {
        @Override
        default String type() {
            return LONG_TYPE_TYPENAME;
        };

    }

    @Immutable
    interface _DateTimeType extends BaseFieldValue<Instant> {
        @Override
        default String type() {
            return DATETIME_TYPE_TYPENAME;
        };
    }

   @Immutable
   @JsonDeserialize(as = FloatType.class)
   interface _FloatType extends BaseFieldValue<Float> {

        @Override
        default String type() {
            return FLOAT_TYPE_TYPENAME;
        };
    }

    @Immutable
    interface  _ListType<T> extends
            BaseFieldValue<List<BaseFieldValue<T>>> {

        @Override
        default String type() {
            return LIST_TYPE_TYPENAME;
        };
    }

}
