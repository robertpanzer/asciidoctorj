package org.asciidoctor;

import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class WhenDocumentContainsDitaaDiagram {

    @Rule
    public ClasspathResources classpath = new ClasspathResources();

    @Test
    public void png_should_be_rendered_for_diagram(@ArquillianResource Asciidoctor asciidoctor) {
        File inputFile = classpath.getResource("sample.adoc");
        File outputFile1 = new File(inputFile.getParentFile(), "asciidoctor-diagram-process.png");
        File outputFile2 = new File(inputFile.getParentFile(), "asciidoctor-diagram-process.png.cache");
        asciidoctor.requireLibrary("asciidoctor-diagram");
        asciidoctor.convertFile(inputFile, options().backend("html5").get());
        assertThat(outputFile1.exists(), is(true));
        assertThat(outputFile2.exists(), is(true));
        outputFile1.delete();
        outputFile2.delete();
    }
}
