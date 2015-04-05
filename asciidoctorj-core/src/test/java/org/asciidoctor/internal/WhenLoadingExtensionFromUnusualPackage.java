package org.asciidoctor.internal;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import unusual.extension.BoldifyPostProcessor;

@RunWith(Arquillian.class)
public class WhenLoadingExtensionFromUnusualPackage {

  @Test
  public void shouldAllowLoadingUsingInstance(@ArquillianResource Asciidoctor asciidoctor) {
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(new unusual.extension.BoldifyPostProcessor());
  }

  @Test
  public void shouldAllowLoadingByClassName(@ArquillianResource Asciidoctor asciidoctor) {
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(BoldifyPostProcessor.class.getCanonicalName());
  }

  @Test
  public void shouldAllowLoadingByClass(@ArquillianResource Asciidoctor asciidoctor) {
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(BoldifyPostProcessor.class);
  }

}
