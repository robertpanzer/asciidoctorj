package org.asciidoctor.converter;

import org.arquillian.jruby.api.RubyResource;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.jboss.arquillian.junit.Arquillian;
import org.jruby.Ruby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class WhenConverterIsRegistered {

    private Asciidoctor asciidoctor;

    @After
    public void cleanUp() {
        asciidoctor.javaConverterRegistry().unregisterAll();
        asciidoctor = null;
    }

    @Test
    public void shouldCleanUpRegistry(@RubyResource Ruby rubyInstance) {
        asciidoctor = JRubyAsciidoctor.create(rubyInstance);

        asciidoctor.javaConverterRegistry().unregisterAll();

        assertThat(asciidoctor.javaConverterRegistry().converters().keySet(), empty());
    }

    @Test
    public void shouldRegisterAndExecuteGivenConverter(@RubyResource Ruby rubyInstance) {
        asciidoctor = JRubyAsciidoctor.create(rubyInstance);

        asciidoctor.javaConverterRegistry().register(TextConverter.class, "test");

        String result = asciidoctor.render("== Hello\n\nWorld!\n\n- a\n- b", OptionsBuilder.options().backend("test"));

        assertThat(result, is("== Hello ==\n\nWorld!\n\n-> a\n-> b\n"));
    }

    @Test
    public void shouldReturnRegisteredConverter(@RubyResource Ruby rubyInstance) {
        asciidoctor = JRubyAsciidoctor.create(rubyInstance);
        asciidoctor.javaConverterRegistry().register(TextConverter.class, "test2");
        assertEquals(TextConverter.class, asciidoctor.javaConverterRegistry().converters().get("test2"));
    }

}
