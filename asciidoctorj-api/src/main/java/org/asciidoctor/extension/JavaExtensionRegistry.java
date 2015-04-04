package org.asciidoctor.extension;

public interface JavaExtensionRegistry {

    void docinfoProcessor(DocinfoProcessor docInfoProcessor);

    void docinfoProcessor(Class<? extends DocinfoProcessor> docInfoProcessor);

    void docinfoProcessor(String docInfoProcessor);

    void preprocessor(Class<? extends Preprocessor> preprocessor);

    void preprocessor(Preprocessor preprocessor);

    void preprocessor(String preprocessor);

    void postprocessor(String postprocessor);

    void postprocessor(Class<? extends Postprocessor> postprocessor);

    void postprocessor(Postprocessor postprocessor);

    void includeProcessor(String includeProcessor);

    void includeProcessor(
            Class<? extends IncludeProcessor> includeProcessor);

    void includeProcessor(IncludeProcessor includeProcessor);

    void treeprocessor(Treeprocessor treeprocessor);

    void treeprocessor(Class<? extends Treeprocessor> abstractTreeProcessor);

    void treeprocessor(String treeProcessor);

    void block(String blockName,
               String blockProcessor);

    void block(String blockName,
               Class<? extends BlockProcessor> blockProcessor);

    void block(BlockProcessor blockProcessor);

    void block(String blockName,
               BlockProcessor blockProcessor);

    void blockMacro(String blockName,
                    Class<? extends BlockMacroProcessor> blockMacroProcessor);

    void blockMacro(String blockName,
                    String blockMacroProcessor);

    void blockMacro(BlockMacroProcessor blockMacroProcessor);

    void inlineMacro(InlineMacroProcessor inlineMacroProcessor);

    void inlineMacro(String blockName,
                     Class<? extends InlineMacroProcessor> inlineMacroProcessor);

    void inlineMacro(String blockName, String inlineMacroProcessor);

}
