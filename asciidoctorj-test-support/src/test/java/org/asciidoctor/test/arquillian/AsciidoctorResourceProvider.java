package org.asciidoctor.test.arquillian;

import org.arquillian.jruby.util.AnnotationUtils;
import org.asciidoctor.Asciidoctor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class AsciidoctorResourceProvider implements ResourceProvider {

    @Inject @ApplicationScoped
    private Instance<ScopedAsciidoctor> scopedAsciidoctorInstance;

    @Override
    public boolean canProvide(Class<?> type) {
        return Asciidoctor.class == type;
    }

    @Override
    public Object lookup(ArquillianResource resource, Annotation... qualifiers) {
        if (AnnotationUtils.filterAnnotation(qualifiers, ResourceProvider.MethodInjection.class) != null) {
            return scopedAsciidoctorInstance.get().getTestScopedAsciidoctor();
        } else if (AnnotationUtils.filterAnnotation(qualifiers, ResourceProvider.ClassInjection.class) != null) {
            return scopedAsciidoctorInstance.get().getClassScopedAsciidoctor();
        } else {
            throw new IllegalArgumentException("Don't know how to resolve Ruby instance with qualifiers " + Arrays.asList(qualifiers));
        }

    }
}
