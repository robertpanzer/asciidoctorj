package org.asciidoctor.extension;

import java.util.HashMap;
import java.util.Map;

public abstract class InlineMacroProcessor extends MacroProcessor {

    public InlineMacroProcessor(String macroName) {
        this(macroName, new HashMap<String, Object>());
    }
    
    public InlineMacroProcessor(String macroName, Map<String, Object> config) {
        super(macroName, config);
    }
}
