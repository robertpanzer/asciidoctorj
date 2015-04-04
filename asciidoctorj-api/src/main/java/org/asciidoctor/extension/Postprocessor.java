package org.asciidoctor.extension;

import org.asciidoctor.ast.DocumentRuby;

import java.util.HashMap;
import java.util.Map;

public abstract class Postprocessor extends Processor {

    public Postprocessor() {
        this(new HashMap<String, Object>());
    }
    
    public Postprocessor(Map<String, Object> config) {
        super(config);
    }

    public abstract String process(DocumentRuby document, String output);

}
