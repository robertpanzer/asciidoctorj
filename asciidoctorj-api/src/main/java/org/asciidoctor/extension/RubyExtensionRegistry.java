package org.asciidoctor.extension;

import java.io.InputStream;

public interface RubyExtensionRegistry {

    RubyExtensionRegistry requireLibrary(String requiredLibrary);

    RubyExtensionRegistry loadClass(InputStream rubyClassStream);

    RubyExtensionRegistry preprocessor(String preprocessor);

    RubyExtensionRegistry postprocessor(String postprocesor);

    RubyExtensionRegistry docinfoProcessor(String docinfoProcessor);

    RubyExtensionRegistry includeProcessor(String includeProcessor);

    RubyExtensionRegistry treeprocessor(String treeProcessor);

    RubyExtensionRegistry block(String blockName, String blockProcessor);

    RubyExtensionRegistry blockMacro(String blockName, String blockMacroProcessor);

    RubyExtensionRegistry inlineMacro(String blockName, String inlineMacroProcessor);
}
