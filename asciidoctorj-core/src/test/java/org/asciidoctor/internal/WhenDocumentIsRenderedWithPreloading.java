package org.asciidoctor.internal;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jruby.Ruby;
import org.jruby.RubyBoolean;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class WhenDocumentIsRenderedWithPreloading {

    @Test
    public void coderay_gem_should_be_preloaded(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().sourceHighlighter("coderay").get()).asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'coderay'");
        assertThat(evalScriptlet.isFalse(), is(true));

    }

    @Test
    public void not_coderay_gem_should_not_be_preloaded(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().sourceHighlighter("pygments").get()).asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'coderay'");
        assertThat(evalScriptlet.isTrue(), is(true));

    }

    @Test
    public void erubis_gem_should_be_preloaded(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options().eruby("erubis").asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'erubis'");
        assertThat(evalScriptlet.isFalse(), is(true));

    }

    @Test
    public void not_erubis_gem_should_be_preloaded(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options().eruby("erb").asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'erubis'");
        assertThat(evalScriptlet.isTrue(), is(true));

    }

    @Test
    public void template_dir_should_preload_tilt(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options().templateDir(new File(".")).asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'tilt'");
        assertThat(evalScriptlet.isFalse(), is(true));

    }

    @Test
    public void data_uri_gem_should_be_preloaded(@ArquillianResource Asciidoctor asciidoctor) {

        Map<String, Object> options = OptionsBuilder.options()
                .attributes(AttributesBuilder.attributes().dataUri(true).get()).asMap();

        JRubyAsciidoctor jRubyAsciidoctor = (JRubyAsciidoctor) asciidoctor;

        jRubyAsciidoctor.rubyGemsPreloader.preloadRequiredLibraries(options);
        RubyBoolean evalScriptlet = (RubyBoolean) jRubyAsciidoctor.rubyRuntime.evalScriptlet("require 'base64'");
        assertThat(evalScriptlet.isFalse(), is(true));

    }

}
