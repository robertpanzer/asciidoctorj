package org.asciidoctor;

import org.arquillian.jruby.api.RubyResource;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jruby.Ruby;
import org.junit.Before;
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
    public void shouldBeRedirectToAsciidoctorJLoggerSystem(@RubyResource Ruby rubyInstance) {
        Asciidoctor asciidoctor = JRubyAsciidoctor.create(rubyInstance);
        File inputFile = classpath.getResource("documentwithnotexistingfile.adoc");
        String renderContent = asciidoctor.renderFile(inputFile, options()
                .inPlace(true).safe(SafeMode.SERVER).asMap());

        File expectedFile = new File(inputFile.getParent(), "documentwithnotexistingfile.html");
        expectedFile.delete();
    }

}
