package org.asciidoctor.extension

import org.asciidoctor.Asciidoctor
import org.asciidoctor.OptionsBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import spock.lang.Specification

class WhenMultipleAsciidoctorInstancesAreCreated extends Specification {


    public static final String ENCODING_UTF8 = 'UTF-8'
    public static final String CLASS = 'class'
    public static final String COMMAND = 'command'

    def "when extensions are registered that create blocks then documents should still be rendered"() {

        given:
        Asciidoctor asciidoctor1 = Asciidoctor.Factory.create()
        Asciidoctor asciidoctor2 = Asciidoctor.Factory.create()

        asciidoctor1.javaExtensionRegistry().treeprocessor(TerminalCommandTreeprocessor)
        asciidoctor2.javaExtensionRegistry().treeprocessor(TerminalCommandTreeprocessor)

        String source1 = '''Hello World

$ echo "Hello, World!"

$ gem install asciidoctor
'''

        String source2 = '''Hello World

$ echo "Hello, Mom!"

$ gem install asciidoctor-diagram
'''

        when:
        String result1 = asciidoctor1.render(source1, OptionsBuilder.options().toFile(false).headerFooter(false))

        and:
        String result2 = asciidoctor2.render(source2, OptionsBuilder.options().toFile(false).headerFooter(false))

        then:
        Document document1 = Jsoup.parse(result1, ENCODING_UTF8)

        Element contentElement1 = document1.getElementsByAttributeValue(CLASS, COMMAND).first()
        contentElement1.text() == 'echo "Hello, World!"'

        Element contentElement2 = document1.getElementsByAttributeValue(CLASS, COMMAND).last()
        contentElement2.text() == 'gem install asciidoctor'

        and:
        Document document2 = Jsoup.parse(result2, ENCODING_UTF8)

        Element contentElement3 = document2.getElementsByAttributeValue(CLASS, COMMAND).first()
        contentElement3.text() == 'echo "Hello, Mom!"'

        Element contentElement4 = document2.getElementsByAttributeValue(CLASS, COMMAND).last()
        contentElement4.text() == 'gem install asciidoctor-diagram'

    }

}
