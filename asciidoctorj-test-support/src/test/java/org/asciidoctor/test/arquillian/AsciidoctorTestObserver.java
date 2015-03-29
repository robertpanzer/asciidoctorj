package org.asciidoctor.test.arquillian;

import org.arquillian.jruby.resources.ScopedResources;
import org.arquillian.jruby.util.AnnotationUtils;
import org.asciidoctor.Asciidoctor;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.event.suite.AfterClass;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AsciidoctorTestObserver {

    @Inject @ApplicationScoped
    private InstanceProducer<ScopedAsciidoctor> scopedAsciidoctor;
    
    @Inject
    private Instance<ScopedResources> scopedRuby;


    public void beforeTestClass(@Observes(precedence = 100) BeforeClass beforeClass) {
        scopedAsciidoctor.set(new ScopedAsciidoctor());

        if (isTestClassRequiringAsciidoctor(beforeClass.getTestClass().getJavaClass())) {
            scopedRuby.get().setCreateTestClassBasedScriptingContainer();
        }
    }

    public void beforeTestClassCreateAsciidoctorInstance(@Observes(precedence = -100) BeforeClass beforeClass) {
        if (isTestClassRequiringAsciidoctor(beforeClass.getTestClass().getJavaClass())) {
            scopedAsciidoctor.get().setClassScopedAsciidoctor(
                    Asciidoctor.Factory.create(scopedRuby.get().getTestScopedScriptingContainer().getProvider().getRuntime()));
        }
    }


    public void beforeTestCreateRubyInstance(@Observes(precedence = 100) Before before) {

        if (isTestMethodUsingParameterInjectedAsciidoctor(before.getTestMethod())) {
            scopedRuby.get().setCreateTestMethodBasedScriptingContainer();
        }

    }

    public void beforeTest(@Observes(precedence = 5) Before before) {

        if (isTestMethodUsingParameterInjectedAsciidoctor(before.getTestMethod())) {
            scopedAsciidoctor.get().setTestScopedAsciidoctor(
                    Asciidoctor.Factory.create(scopedRuby.get().getTestScopedScriptingContainer().getProvider().getRuntime()));
        }

    }

    public void cleanAsciidoctorInstances(@Observes AfterClass afterClass) {
        scopedAsciidoctor.get().setClassScopedAsciidoctor(null);
        scopedAsciidoctor.get().setTestScopedAsciidoctor(null);
    }

    private boolean isTestClassRequiringAsciidoctor(Class<?> testClass) {
        for (Field f: SecurityActions.getFieldsWithAnnotation(testClass, ArquillianResource.class)) {
            if (f.getType() == Asciidoctor.class) {
                return true;
            }
        }
        return false;
    }

    private boolean isTestMethodUsingParameterInjectedAsciidoctor(Method testMethod) {
        for (int i = 0; i < testMethod.getParameterTypes().length; i++) {
            if (testMethod.getParameterTypes()[i] == Asciidoctor.class) {
                if (AnnotationUtils.filterAnnotation(testMethod.getParameterAnnotations()[i], ArquillianResource.class) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
