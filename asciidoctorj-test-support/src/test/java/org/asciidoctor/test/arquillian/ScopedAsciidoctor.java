package org.asciidoctor.test.arquillian;

import org.asciidoctor.Asciidoctor;

public class ScopedAsciidoctor {

    private Asciidoctor classScopedAsciidoctor;

    private Asciidoctor testScopedAsciidoctor;

    public void setClassScopedAsciidoctor(Asciidoctor classScopedAsciidoctor) {
        this.classScopedAsciidoctor = classScopedAsciidoctor;
    }

    public Asciidoctor getClassScopedAsciidoctor() {
        return classScopedAsciidoctor;
    }

    public void setTestScopedAsciidoctor(Asciidoctor testScopedAsciidoctor) {
        this.testScopedAsciidoctor = testScopedAsciidoctor;
    }

    public Asciidoctor getTestScopedAsciidoctor() {
        return testScopedAsciidoctor;
    }

}
