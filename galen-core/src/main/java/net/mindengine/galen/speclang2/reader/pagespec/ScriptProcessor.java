/*******************************************************************************
* Copyright 2015 Ivan Shubin http://mindengine.net
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package net.mindengine.galen.speclang2.reader.pagespec;

import net.mindengine.galen.parser.StructNode;
import net.mindengine.galen.parser.SyntaxException;
import net.mindengine.galen.specs.reader.StringCharReader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ScriptProcessor {
    private final PageSpecHandler pageSpecHandler;

    public ScriptProcessor(PageSpecHandler pageSpecHandler) {
        this.pageSpecHandler = pageSpecHandler;
    }

    public List<StructNode> process(StringCharReader reader, StructNode scriptNode) throws IOException {
        String scriptPath = reader.getTheRest().trim();
        if (scriptPath.isEmpty()) {
            if (scriptNode.hasChildNodes()) {
                String completeScript = scriptNode.assembleAllChildNodes();
                pageSpecHandler.runJavaScript(completeScript);
            } else {
                throw new SyntaxException(scriptNode, "Missing script");
            }
        } else {
            String fullPath = pageSpecHandler.getFullPathToResource(scriptPath);
            pageSpecHandler.runJavaScriptFromFile(fullPath);
        }

        return Collections.emptyList();
    }
}