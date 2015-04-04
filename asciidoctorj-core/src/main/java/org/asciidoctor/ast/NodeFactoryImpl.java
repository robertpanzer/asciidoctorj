package org.asciidoctor.ast;

import org.asciidoctor.Options;
import org.asciidoctor.internal.RubyHashUtil;
import org.asciidoctor.internal.RubyUtils;
import org.jruby.Ruby;
import org.jruby.RubyHash;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.List;
import java.util.Map;

public class NodeFactoryImpl implements NodeFactory {


    @Override
    public Block createBlock(AbstractBlock parent, String context, String content, Map<String, Object> attributes, Map<String, Object> options) {
        options.put(Options.SOURCE, content);
        options.put(Options.ATTRIBUTES, attributes);

        return createBlock(parent, context, options);

    }

    @Override
    public Block createBlock(AbstractBlock parent, String context, List<String> content, Map<String, Object> attributes, Map<String, Object> options) {
        options.put(Options.SOURCE, content);
        options.put(Options.ATTRIBUTES, attributes);

        return createBlock(parent, context, options);
    }

    @Override
    public Inline createInline(AbstractBlock parent, String context, String text, Map<String, Object> attributes, Map<String, Object> options) {

        options.put(Options.ATTRIBUTES, attributes);

        Ruby rubyRuntime = getRubyRuntime(parent);

        IRubyObject rubyClass = rubyRuntime.evalScriptlet("Asciidoctor::Inline");
        RubyHash convertedOptions = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime, options);
        // FIXME hack to ensure we have the underlying Ruby instance
        try {
            parent = parent.delegate();
        } catch (Exception e) {}

        Object[] parameters = {
                parent,
                RubyUtils.toSymbol(rubyRuntime, context),
                text,
                convertedOptions };
        return (Inline) JavaEmbedUtils.invokeMethod(rubyRuntime, rubyClass,
                "new", parameters, Inline.class);

    }


    @Override
    public Inline createInline(AbstractBlock parent, String context, List<String> text, Map<String, Object> attributes, Map<String, Object> options) {
        options.put(Options.ATTRIBUTES, attributes);

        Ruby rubyRuntime = getRubyRuntime(parent);

        IRubyObject rubyClass = rubyRuntime.evalScriptlet("Asciidoctor::Inline");
        RubyHash convertMapToRubyHashWithSymbols = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime,
                options);
        Object[] parameters = {
                parent.delegate(),
                RubyUtils.toSymbol(rubyRuntime, context),
                text,
                convertMapToRubyHashWithSymbols };
        return (Inline) JavaEmbedUtils.invokeMethod(rubyRuntime, rubyClass,
                "new", parameters, Inline.class);

    }

    private Block createBlock(AbstractBlock parent, String context, Map<String, Object> options) {

        Ruby rubyRuntime = getRubyRuntime(parent);

        IRubyObject rubyClass = rubyRuntime.evalScriptlet("Asciidoctor::Block");
        RubyHash convertMapToRubyHashWithSymbols = RubyHashUtil.convertMapToRubyHashWithSymbols(rubyRuntime,
                options);
        // FIXME hack to ensure we have the underlying Ruby instance
        try {
            parent = parent.delegate();
        } catch (Exception e) {}

        Object[] parameters = {
                parent,
                RubyUtils.toSymbol(rubyRuntime, context),
                convertMapToRubyHashWithSymbols };
        return (Block) JavaEmbedUtils.invokeMethod(rubyRuntime, rubyClass,
                "new", parameters, Block.class);
    }

    private Ruby getRubyRuntime(AbstractBlock parent) {
        return ((AbstractNodeImpl) parent).getRuntime();
    }

}
