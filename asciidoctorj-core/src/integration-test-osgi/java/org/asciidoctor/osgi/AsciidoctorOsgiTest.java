package org.asciidoctor.osgi;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;

@RunWith(Arquillian.class)
public class AsciidoctorOsgiTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File(System.getProperty("asciidoctorJarName")));
    }


    @ArquillianResource
    private BundleContext ctx;

    @Test
    public void test() throws Exception {
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        String result = asciidoctor.convert("= Hello World", OptionsBuilder.options().toFile(false).headerFooter(false));
        System.out.println(result);
        assertThat(result, containsString("Hello World"));
    }

}
