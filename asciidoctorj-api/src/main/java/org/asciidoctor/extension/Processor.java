package org.asciidoctor.extension;

import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Inline;
import org.asciidoctor.ast.NodeFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {

	protected Map<String, Object> config;

    public Processor(Map<String, Object> config) {
        this.config = new HashMap<String, Object>(config);
    }

    public void update_config(Map<String, Object> config) {
    	this.config.putAll(config);
    }

    public Map<String, Object> getConfig() {
    	return this.config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = new HashMap<String, Object>(config);
    }

    public Block createBlock(AbstractBlock parent, String context, String content, Map<String, Object> attributes,
            Map<String, Object> options) {

        return NodeFactory.Provider.getInstance().createBlock(parent, context, content, attributes, options);

    }
    
    public Block createBlock(AbstractBlock parent, String context, List<String> content, Map<String, Object> attributes,
            Map<String, Object> options) {

        return NodeFactory.Provider.getInstance().createBlock(parent, context, content, attributes, options);

    }

    public Inline createInline(AbstractBlock parent, String context, List<String> text, Map<String, Object> attributes, Map<String, Object> options) {

        return NodeFactory.Provider.getInstance().createInline(parent, context, text, attributes, options);

    }
    
    public Inline createInline(AbstractBlock parent, String context, String text, Map<String, Object> attributes, Map<String, Object> options) {

        return NodeFactory.Provider.getInstance().createInline(parent, context, text, attributes, options);

    }
}
