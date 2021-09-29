package com.dotcms.newcontentlet;


import com.dotcms.newcontentlet.datatype.BaseFieldValue;
import com.dotcms.newcontentlet.datatype.annotations.VersionedModel;
import com.dotcms.newcontentlet.datatype.versions.TestV1ModelConverter;
import com.dotcms.newcontentlet.datatype.views.JsonViews;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jonpeterson.jackson.module.versioning.JsonSerializeToVersion;
import com.github.jonpeterson.jackson.module.versioning.JsonVersionedModel;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@VersionedModel
@JsonDeserialize(builder = ImmutableContentlet.Builder.class)
@JsonVersionedModel(currentVersion = "2", toCurrentConverterClass = TestV1ModelConverter.class, toPastConverterClass = TestV1ModelConverter.class)
public interface Contentlet {


    @Nullable
    @JsonProperty
    @JsonSerializeToVersion(defaultToSource = true)
    String modelVersion();

    @JsonView(JsonViews.SummaryView.class)
    String title();
    @JsonView(JsonViews.SummaryView.class)
    String iNode();
    String identifier();
    String contentType();
    Instant modDate();
    String baseType();
    boolean showOnMenu();
    String modUser();
    String languageId();
    String editState();
    String friendlyName();
    List<String> tags();
    Map<String, BaseFieldValue<?>> fields();
}
