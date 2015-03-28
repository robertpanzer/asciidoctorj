package org.asciidoctor;

import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;

import static org.asciidoctor.OptionsBuilder.options;

@RunWith(Arquillian.class)
public class WhenAsciidoctorLogsToConsole {

    @Rule
    public ClasspathResources classpath = new ClasspathResources();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void shouldBeRedirectToAsciidoctorJLoggerSystem(@ArquillianResource Asciidoctor asciidoctor) {
        File inputFile = classpath.getResource("documentwithnotexistingfile.adoc");
        String renderContent = asciidoctor.renderFile(inputFile, options()
                .inPlace(true).safe(SafeMode.SERVER).asMap());

        File expectedFile = new File(inputFile.getParent(), "documentwithnotexistingfile.html");
        expectedFile.delete();
    }

}
