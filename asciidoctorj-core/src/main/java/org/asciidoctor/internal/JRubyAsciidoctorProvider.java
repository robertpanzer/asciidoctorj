package org.asciidoctor.internal;

import org.asciidoctor.Asciidoctor;
import org.jruby.Ruby;

import java.util.List;

public class JRubyAsciidoctorProvider implements AsciidoctorProvider {


    @Override
    public Asciidoctor create() {
        return JRubyAsciidoctor.create();
    }

    @Override
    public Asciidoctor create(String gemPath) {
        return JRubyAsciidoctor.create(gemPath);
    }

    @Override
    public Asciidoctor create(List<String> loadPaths) {
        return JRubyAsciidoctor.create(loadPaths);
    }

    @Override
    public Asciidoctor create(ClassLoader classloader) {
        return JRubyAsciidoctor.create(classloader);
    }

    @Override
    public Asciidoctor create(ClassLoader classloader, String gemPath) {
        return JRubyAsciidoctor.create(classloader, gemPath);
    }
}
