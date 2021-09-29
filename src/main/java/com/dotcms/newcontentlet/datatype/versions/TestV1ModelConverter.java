package com.dotcms.newcontentlet.datatype.versions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jonpeterson.jackson.module.versioning.VersionedModelConverter;

public class TestV1ModelConverter implements VersionedModelConverter {

    @Override
    public ObjectNode convert(ObjectNode modelData, String modelVersion,
            String targetModelVersion, JsonNodeFactory nodeFactory) {
        // model version is an int
        int version = Integer.parseInt(modelVersion);
        int target = Integer.parseInt(targetModelVersion);

        // version 1 had a single 'model' field that combined 'make' and 'model' with a colon delimiter
        if (version <= 1 && target >= 1) {
            String data = modelData.get("oldId").asText();
            modelData.put("iNode", "v2id-" + data);
            modelData.remove("oldId");
        }

        if (version > 1 && target == 1) {
            String data = modelData.get("iNode").asText();
            modelData.put("oldId", data.substring(data.lastIndexOf("v2id-") + 1));
            modelData.remove("iNode");
        }

        return modelData;
    }
}
