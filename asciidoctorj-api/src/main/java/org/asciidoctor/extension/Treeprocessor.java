package org.asciidoctor.extension;

import org.asciidoctor.ast.DocumentRuby;

import java.util.HashMap;
import java.util.Map;

public abstract class Treeprocessor extends Processor {

    public Treeprocessor() {
        this(new HashMap<String, Object>());
    }
    
    public Treeprocessor(Map<String, Object> config) {
        super(config);
    }

    public abstract DocumentRuby process(DocumentRuby document);
    
}
