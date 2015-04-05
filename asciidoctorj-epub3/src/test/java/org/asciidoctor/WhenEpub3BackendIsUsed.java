package org.asciidoctor;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.asciidoctor.internal.JRubyAsciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WhenEpub3BackendIsUsed {

    @ArquillianResource
    public ClasspathResources classpath;
    
    @Test
    public void epub3_should_be_rendered_for_epub3_backend(@ArquillianResource Asciidoctor asciidoctor) {
        File inputFile = classpath.getResource("epub-index.adoc");
        File outputFile = new File(inputFile.getParentFile(), "epub-index.epub");
        asciidoctor.convertFile(inputFile, options().safe(SafeMode.SAFE).backend("epub3").get());
        assertThat(outputFile.exists(), is(true));
    }
    
}
