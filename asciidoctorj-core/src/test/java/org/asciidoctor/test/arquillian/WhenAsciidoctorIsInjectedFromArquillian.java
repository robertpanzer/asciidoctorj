package org.asciidoctor.test.arquillian;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.test.arquillian.api.Shared;
import org.asciidoctor.test.arquillian.api.Unshared;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

// This test tests the arquillian asciidoctor extension itself
@RunWith(Arquillian.class)
public class WhenAsciidoctorIsInjectedFromArquillian {

    @ArquillianResource
    private Asciidoctor unspecifiedClassAsciidoctor;

    @ArquillianResource(Shared.class)
    private Asciidoctor sharedClassAsciidoctor;

    @ArquillianResource(Unshared.class)
    private Asciidoctor unsharedClassAsciidoctor;

    private static Asciidoctor lastSharedAsciidoctor;
    private static Asciidoctor lastUnsharedAsciidoctor;

    @Test
    @InSequence(1)
    public void should_inject_Asciidoctor_instances_into_class() {
        assertThat(unspecifiedClassAsciidoctor, notNullValue());
        assertThat(sharedClassAsciidoctor, notNullValue());
        assertThat(unsharedClassAsciidoctor, notNullValue());

        assertThat(unspecifiedClassAsciidoctor, sameInstance(unsharedClassAsciidoctor));
        assertThat(unspecifiedClassAsciidoctor, not(sameInstance(sharedClassAsciidoctor)));
    }

    @Test
    @InSequence(2)
    public void should_inject_Asciidoctor_instances_into_method_params(
            @ArquillianResource Asciidoctor unspecifiedMethodParamAsciidoctor,
            @ArquillianResource(Shared.class) Asciidoctor sharedMethodParamAsciidoctor,
            @ArquillianResource(Unshared.class) Asciidoctor unsharedMethodParamAsciidoctor) {

        assertThat(unspecifiedMethodParamAsciidoctor, notNullValue());

        assertThat(unspecifiedMethodParamAsciidoctor, not(sameInstance(sharedMethodParamAsciidoctor)));

        assertThat(unspecifiedMethodParamAsciidoctor, sameInstance(unsharedMethodParamAsciidoctor));

        assertThat(unspecifiedMethodParamAsciidoctor, sameInstance(unspecifiedClassAsciidoctor));

        assertThat(unspecifiedMethodParamAsciidoctor, not(sameInstance(sharedClassAsciidoctor)));

        assertThat(unspecifiedMethodParamAsciidoctor, sameInstance(unsharedClassAsciidoctor));

        assertThat(sharedMethodParamAsciidoctor, notNullValue());

        lastSharedAsciidoctor = sharedMethodParamAsciidoctor;
        lastUnsharedAsciidoctor = unsharedMethodParamAsciidoctor;
    }

    // Test is coupled to execution of test before.
    // Not nice but I don't know any other way to test what
    // instances are injected between two test methods
    @Test
    @InSequence(3)
    public void should_inject_new_unshared_Asciidoctor_instance_per_test(
            @ArquillianResource Asciidoctor unspecifiedMethodParamAsciidoctor,
            @ArquillianResource(Shared.class) Asciidoctor sharedMethodParamAsciidoctor,
            @ArquillianResource(Unshared.class) Asciidoctor unsharedMethodParamAsciidoctor) {

        assertThat(unspecifiedMethodParamAsciidoctor, not(sameInstance(lastUnsharedAsciidoctor)));
        assertThat(sharedMethodParamAsciidoctor, sameInstance(lastSharedAsciidoctor));
        assertThat(unsharedMethodParamAsciidoctor, not(sameInstance(lastUnsharedAsciidoctor)));
    }
 }
