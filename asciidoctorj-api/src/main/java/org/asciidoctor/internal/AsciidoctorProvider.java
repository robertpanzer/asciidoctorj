package org.asciidoctor.internal;

import org.asciidoctor.Asciidoctor;

import java.util.List;

public interface AsciidoctorProvider {

    Asciidoctor create();

    Asciidoctor create(String gemPath);

    Asciidoctor create(List<String> loadPaths);

    Asciidoctor create(ClassLoader classloader);

    Asciidoctor create(ClassLoader classloader, String gemPath);
}
