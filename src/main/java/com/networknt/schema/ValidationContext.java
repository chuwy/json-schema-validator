/*
 * Copyright (c) 2016 Network New Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.networknt.schema;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.uri.URIFactory;

public class ValidationContext {
    private final URIFactory uriFactory;
    private final JsonMetaSchema metaSchema;
    private final JsonSchemaFactory jsonSchemaFactory;
    private SchemaValidatorsConfig config;
    private final Map<String, JsonSchemaRef> refParsingInProgress = new HashMap<>();

    public ValidationContext(URIFactory uriFactory, JsonMetaSchema metaSchema, JsonSchemaFactory jsonSchemaFactory) {
        if (uriFactory == null) {
            throw new IllegalArgumentException("URIFactory must not be null");
        }
        if (metaSchema == null) {
            throw new IllegalArgumentException("JsonMetaSchema must not be null");
        }
        if (jsonSchemaFactory == null) {
            throw new IllegalArgumentException("JsonSchemaFactory must not be null");
        }
        this.uriFactory = uriFactory;
        this.metaSchema = metaSchema;
        this.jsonSchemaFactory = jsonSchemaFactory;
    }

    public JsonValidator newValidator(String schemaPath, String keyword /* keyword */, JsonNode schemaNode,
                                      JsonSchema parentSchema) {
        return metaSchema.newValidator(this, schemaPath, keyword, schemaNode, parentSchema);
    }

    public URIFactory getURIFactory() {
        return this.uriFactory;
    }

    public JsonSchemaFactory getJsonSchemaFactory() {
        return jsonSchemaFactory;
    }

    public SchemaValidatorsConfig getConfig() {
        return config;
    }

    public void setConfig(SchemaValidatorsConfig config) {
        this.config = config;
    }

    public void setReferenceParsingInProgress(String refValue, JsonSchemaRef ref) {
        refParsingInProgress.put(refValue, ref);
    }

    public JsonSchemaRef getReferenceParsingInProgress(String refValue) {
        return refParsingInProgress.get(refValue);
    }

}
