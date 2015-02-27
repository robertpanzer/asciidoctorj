package org.asciidoctor;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.arquillian.jruby.api.RubyResource;
import org.asciidoctor.internal.JRubyAsciidoctor;
import org.jboss.arquillian.junit.Arquillian;
import org.jruby.Ruby;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WhenUserRequiresTheAsciidoctorRuntimeVersion {
    @Test
    public void current_version_should_be_retrieved(@RubyResource Ruby rubyInstance) {
        
        Asciidoctor asciidoctor = Asciidoctor.Factory.create(rubyInstance);
        String asciidoctorVersion = asciidoctor.asciidoctorVersion();
        
        assertThat(asciidoctorVersion, is(notNullValue()));
        
    }
    
}
