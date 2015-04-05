package org.asciidoctor;

import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.ast.Section;
import org.asciidoctor.internal.IOUtils;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class WhenAsciiDocIsRenderedToDocument {

    private static final String DOCUMENT = "= Document Title\n" +
            "\n" + 
            "preamble\n" + 
            "\n" + 
            "== Section A\n" + 
            "\n" + 
            "paragraph\n" + 
            "\n" + 
            "--\n" + 
            "Exhibit A::\n" + 
            "+\n" + 
            "[#tiger.animal]\n" + 
            "image::tiger.png[Tiger]\n" + 
            "--\n" + 
            "\n" + 
            "image::cat.png[Cat]\n" + 
            "\n" + 
            "== Section B\n" + 
            "\n" + 
            "paragraph";

    private static final String ROLE = "[\"quote\", \"author\", \"source\", role=\"famous\"]\n" +
            "____\n" +
            "A famous quote.\n" +
            "____";

    private static final String REFTEXT = "[reftext=\"the first section\"]\n" +
            "== Section One\n" +
            "\n" +
            "content";

    @ArquillianResource
    public ClasspathResources classpath;

    @Test
    public void should_return_section_blocks(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        Section section = (Section) document.blocks().get(1);
        assertThat(section.index(), is(0));
        assertThat(section.sectname(), is("sect1"));
        assertThat(section.special(), is(false));
    }
    
    @Test
    public void should_return_blocks_from_a_document(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.doctitle(), is("Document Title"));
        
    }
    
    @Test
    public void should_return_a_document_object_from_string(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.doctitle(), is("Document Title"));
    }
    
    @Test
    public void should_find_elements_from_document(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("context", ":image");
        List<AbstractBlock> findBy = document.findBy(selector);
        assertThat(findBy, hasSize(2));
        
        assertThat((String)findBy.get(0).getAttributes().get("target"), is("tiger.png"));
        
    }

    @Test
    public void should_return_options_from_document(@ArquillianResource Asciidoctor asciidoctor) {
        Map<String, Object> options = OptionsBuilder.options().compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);

        Map<Object, Object> documentOptions = document.getOptions();

        assertThat((Boolean) documentOptions.get("compact"), is(true));
    }

    @Test
    public void should_return_node_name(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.getNodeName(), is("document"));
    }

    @Test
    public void should_return_if_it_is_inline(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.isInline(), is(false));
    }

    @Test
    public void should_return_if_it_is_block(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.isBlock(), is(true));
    }

    @Test
    public void should_be_able_to_manipulate_attributes(@ArquillianResource Asciidoctor asciidoctor) {
        Map<String, Object> options = OptionsBuilder.options()
                                                    .attributes(AttributesBuilder.attributes().dataUri(true))
                                                    .compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);
        assertThat(document.getAttributes(), hasKey("encoding"));
        assertThat(document.isAttr("encoding", "UTF-8", false), is(true));
        assertThat(document.getAttr("encoding", "", false).toString(), is("UTF-8"));
    }

    @Test
    public void should_be_able_to_get_roles(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(ROLE, new HashMap<String, Object>());
        AbstractBlock abstractBlock = document.blocks().get(0);
        assertThat(abstractBlock.getRole(), is("famous"));
        assertThat(abstractBlock.hasRole("famous"), is(true));
        //assertThat(abstractBlock.isRole(), is(true));
        assertThat(abstractBlock.getRoles(), contains("famous"));
    }

    @Test
    public void should_be_able_to_get_reftext(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(REFTEXT, new HashMap<String, Object>());
        AbstractBlock abstractBlock = document.blocks().get(0);
        assertThat(abstractBlock.getReftext(), is("the first section"));
        assertThat(abstractBlock.isReftext(), is(true));
    }

    @Test
    public void should_be_able_to_get_icon_uri_string_reference(@ArquillianResource Asciidoctor asciidoctor) {
        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);
        assertThat(document.iconUri("note"), is("./images/icons/note.png"));
    }

    @Test
    public void should_be_able_to_get_icon_uri(@ArquillianResource Asciidoctor asciidoctor) {
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(true).icons("font"))
                .compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);
        assertThat(document.iconUri("note"), is("data:image/png:base64,"));
    }

    @Test
    public void should_be_able_to_get_media_uri(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.mediaUri("target"), is("target"));
    }

    @Test
    public void should_be_able_to_get_image_uri(@ArquillianResource Asciidoctor asciidoctor) {
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);
        assertThat(document.imageUri("target.jpg"), is("target.jpg"));
        assertThat(document.imageUri("target.jpg", "imagesdir"), is("target.jpg"));
    }

    @Test
    public void should_be_able_to_normalize_web_path(@ArquillianResource Asciidoctor asciidoctor) {
        DocumentRuby document = asciidoctor.load(DOCUMENT, new HashMap<String, Object>());
        assertThat(document.normalizeWebPath("target", null, true), is("target"));
    }

    @Test
    public void should_be_able_to_read_asset(@ArquillianResource Asciidoctor asciidoctor) throws FileNotFoundException {
        Map<String, Object> options = OptionsBuilder.options().safe(SafeMode.SAFE)
                .attributes(AttributesBuilder.attributes().dataUri(false))
                .compact(true).asMap();
        DocumentRuby document = asciidoctor.load(DOCUMENT, options);
        File inputFile = classpath.getResource("rendersample.asciidoc");
        String content = document.readAsset(inputFile.getAbsolutePath(), new HashMap<Object, Object>());
        assertThat(content, is(IOUtils.readFull(new FileReader(inputFile))));
    }

}
