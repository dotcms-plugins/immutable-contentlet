package com.dotcms.newcontentlet.datatype.annotations;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import org.immutables.value.Value;
import org.immutables.value.internal.$processor$.meta.$ValueMirrors;

/*@Value.Style(
        additionalJsonAnnotations = {JsonVersionedModel.class}
)
 */
@JsonSerialize()
public @interface VersionedModel {
}
