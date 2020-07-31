/*
 * Copyright 2017 The Embulk project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.embulk.base.restclient.jackson.scope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.embulk.base.restclient.record.SinglePageRecordReader;

public class JacksonNewObjectScope extends JacksonObjectScopeBase {
    @Override
    public ObjectNode scopeObject(final SinglePageRecordReader singlePageRecordReader) {
        return OBJECT_MAPPER.createObjectNode();
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
