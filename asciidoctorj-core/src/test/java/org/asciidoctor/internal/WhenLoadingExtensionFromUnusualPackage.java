package org.asciidoctor.internal;

import org.arquillian.jruby.api.RubyResource;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.SafeMode;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.extension.BlockMacroProcessor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jruby.Ruby;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import unusual.extension.BoldifyPostProcessor;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import static org.asciidoctor.OptionsBuilder.options;

@RunWith(Arquillian.class)
public class WhenLoadingExtensionFromUnusualPackage {

  @Rule
  public ClasspathResources classpath = new ClasspathResources();

  @Test
  public void shouldAllowLoadingUsingInstance(@RubyResource Ruby rubyInstance) {
    Asciidoctor asciidoctor = JRubyAsciidoctor.create(rubyInstance);
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(new unusual.extension.BoldifyPostProcessor());
  }

  @Test
  public void shouldAllowLoadingByClassName(@RubyResource Ruby rubyInstance) {
    Asciidoctor asciidoctor = JRubyAsciidoctor.create(rubyInstance);
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(BoldifyPostProcessor.class.getCanonicalName());
  }

  @Test
  public void shouldAllowLoadingByClass(@RubyResource Ruby rubyInstance) {
    Asciidoctor asciidoctor = JRubyAsciidoctor.create(rubyInstance);
    final JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
    registry.postprocessor(BoldifyPostProcessor.class);
  }

}
