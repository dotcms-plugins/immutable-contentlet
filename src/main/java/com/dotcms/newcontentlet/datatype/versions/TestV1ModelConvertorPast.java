package com.dotcms.newcontentlet.datatype.versions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;

public class TestV1ModelConvertorPast implements VersionedModelConverter {

    @Override
    public ObjectNode convert(ObjectNode modelData, String modelVersion,
            String targetModelVersion, JsonNodeFactory nodeFactory) {
        // model version is an int
        int version = Integer.parseInt(modelVersion);
        int targetVersion = Integer.parseInt(modelVersion);

        // version 1 had a single 'model' field that combined 'make' and 'model' with a colon delimiter
        if (version <= 1) {
            String data = modelData.get("oldId").asText();
            modelData.put("iNode", "newid-" + data);
        }

        return modelData;
    }
}
