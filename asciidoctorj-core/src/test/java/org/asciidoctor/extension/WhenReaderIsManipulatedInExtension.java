package org.asciidoctor.extension;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class WhenReaderIsManipulatedInExtension {

    @Rule
    public ClasspathResources classpath = new ClasspathResources();

	@Test
	public void currentLineNumberShouldBeReturned(@ArquillianResource Asciidoctor asciidoctor) {

        JavaExtensionRegistry javaExtensionRegistry = asciidoctor
				.javaExtensionRegistry();

		javaExtensionRegistry.preprocessor(NumberLinesPreprocessor.class);

		File inputFile = classpath.getResource("rendersample.asciidoc");
		asciidoctor.renderFile(inputFile, new HashMap<String, Object>());
		
		File outpuFile = new File(inputFile.getParent(), "rendersample.asciidoc");
		assertThat(outpuFile.exists(), is(true));
	}

	@Test
	public void hasMoreLinesShouldBeReturned(@ArquillianResource Asciidoctor asciidoctor) {

		JavaExtensionRegistry javaExtensionRegistry = asciidoctor
				.javaExtensionRegistry();

		javaExtensionRegistry.preprocessor(HasMoreLinesPreprocessor.class);

        asciidoctor.renderFile(
                classpath.getResource("rendersample.asciidoc"),
                new HashMap<String, Object>());

        File inputFile = classpath.getResource("rendersample.asciidoc");
        asciidoctor.renderFile(inputFile, new HashMap<String, Object>());

        File outpuFile = new File(inputFile.getParent(), "rendersample.asciidoc");
        assertThat(outpuFile.exists(), is(true));
	}
	
	@Test
	public void isNextLineEmptyShouldBeReturned(@ArquillianResource Asciidoctor asciidoctor) {

		JavaExtensionRegistry javaExtensionRegistry = asciidoctor
				.javaExtensionRegistry();

		javaExtensionRegistry.preprocessor(NextLineEmptyPreprocessor.class);

        asciidoctor.renderFile(
                classpath.getResource("rendersample.asciidoc"),
                new HashMap<String, Object>());
        
        File inputFile = classpath.getResource("rendersample.asciidoc");
        asciidoctor.renderFile(inputFile, new HashMap<String, Object>());

        File outpuFile = new File(inputFile.getParent(), "rendersample.asciidoc");
        assertThat(outpuFile.exists(), is(true));
	}
	
}
