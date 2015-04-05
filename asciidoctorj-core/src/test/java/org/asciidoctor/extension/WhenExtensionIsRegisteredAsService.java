package org.asciidoctor.extension;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Options;
import org.asciidoctor.SafeMode;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WhenExtensionIsRegisteredAsService {

    @ArquillianResource
    public ClasspathResources classpath;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Ignore("Test is ignored because currently it is not possible to register two block extensions in same instance. This may require deep changes on Asciidoctor Extensions API")
    @Test
    public void extensions_should_be_correctly_added(@ArquillianResource Asciidoctor asciidoctor) throws IOException {

        //To avoid registering the same extension over and over for all tests, service is instantiated manually.
        new ArrowsAndBoxesExtension().register(asciidoctor);
        
        Options options = options().inPlace(false)
                .toFile(new File(testFolder.getRoot(), "rendersample.html"))
                .safe(SafeMode.UNSAFE).get();

        asciidoctor.renderFile(classpath.getResource("arrows-and-boxes-example.ad"), options);

        File renderedFile = new File(testFolder.getRoot(), "rendersample.html");
        Document doc = Jsoup.parse(renderedFile, "UTF-8");
        
        Element arrowsJs = doc.select("script[src=http://www.headjump.de/javascripts/arrowsandboxes.js").first();
        assertThat(arrowsJs, is(notNullValue()));
        
        Element arrowsCss = doc.select("link[href=http://www.headjump.de/stylesheets/arrowsandboxes.css").first();
        assertThat(arrowsCss, is(notNullValue()));
        
        Element arrowsAndBoxes = doc.select("pre[class=arrows-and-boxes").first();
        assertThat(arrowsAndBoxes, is(notNullValue()));
        
    }

}
