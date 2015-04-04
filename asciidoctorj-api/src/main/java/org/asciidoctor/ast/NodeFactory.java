package org.asciidoctor.ast;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public interface NodeFactory {

    class Provider {

        private static NodeFactory instance;

        public static NodeFactory getInstance() {

            if (instance == null) {
                instance = ServiceLoader.load(NodeFactory.class).iterator().next();
            }

            return instance;
        }

    }

    Block createBlock(AbstractBlock parent, String context, String content, Map<String, Object> attributes, Map<String, Object> options);

    Block createBlock(AbstractBlock parent, String context, List<String> content, Map<String, Object> attributes, Map<String, Object> options);

    Inline createInline(AbstractBlock parent, String context, String text, Map<String, Object> attributes, Map<String, Object> options);

    Inline createInline(AbstractBlock parent, String context, List<String> text, Map<String, Object> attributes, Map<String, Object> options);


}
