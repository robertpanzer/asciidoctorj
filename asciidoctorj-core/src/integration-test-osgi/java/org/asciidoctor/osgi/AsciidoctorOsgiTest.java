package org.asciidoctor.osgi;

import org.junit.Test;
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
        return ShrinkWrap.createFromZipFile(JavaArchive.class, new File("build/libs/asciidoctorj-1.6.0-SNAPSHOT.jar"));
    }


    @ArquillianResource
    private BundleContext ctx;

    @Test
    public void test() throws Exception {
        Bundle[] bundles = ctx.getBundles();
        System.out.println(Arrays.asList(bundles));
    }

}
