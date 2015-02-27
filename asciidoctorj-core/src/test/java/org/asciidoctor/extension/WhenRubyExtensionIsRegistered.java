package org.asciidoctor.extension;

import static org.asciidoctor.OptionsBuilder.options;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.arquillian.jruby.api.RubyResource;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.asciidoctor.util.ClasspathResources;
import org.jboss.arquillian.junit.Arquillian;
import org.jruby.Ruby;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WhenRubyExtensionIsRegistered {

    @Rule
    public ClasspathResources classpath = new ClasspathResources();

    @Test
    public void ruby_extension_should_be_registered(@RubyResource Ruby rubyInstance) {

        Asciidoctor asciidoctor = JRubyAsciidoctor.create(rubyInstance);

        RubyExtensionRegistry rubyExtensionRegistry = asciidoctor.rubyExtensionRegistry();
        rubyExtensionRegistry.loadClass(Class.class.getResourceAsStream("/YellRubyBlock.rb")).block("rubyyell", "YellRubyBlock");

        String content = asciidoctor.renderFile(
                classpath.getResource("sample-with-ruby-yell-block.ad"),
                options().toFile(false).get());

        Document doc = Jsoup.parse(content, "UTF-8");
        Elements elements = doc.getElementsByClass("paragraph");
        assertThat(elements.size(), is(2));
        assertThat(elements.get(1).text(), is("THE TIME IS NOW! GET A MOVE ON!"));
        
    }

}
