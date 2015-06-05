package org.asciidoctor.converter

import org.asciidoctor.Asciidoctor
import org.asciidoctor.OptionsBuilder
import org.jboss.arquillian.spock.ArquillianSputnik
import org.jboss.arquillian.test.api.ArquillianResource
import org.junit.runner.RunWith
import spock.lang.Specification

@RunWith(ArquillianSputnik)
class TableConverterTest extends Specification {

    private static final String DOCUMENT = '''= Hello Table

|====
|       | Column A | Column B

| Row 1 |   A1     |    B1
| Row 2 |   A2     |    B2
|====
'''

    @ArquillianResource
    private Asciidoctor asciidoctor

    def "should convert a table"() {
        given:
        asciidoctor.javaConverterRegistry().register(TableTestConverter, 'tabletestconverter')

        when:
        String content = asciidoctor.convert(DOCUMENT, OptionsBuilder.options().headerFooter(false).backend('tabletestconverter'))

        then:
        content.replaceAll(' ', '') == '''               |Column A       |Column B
---------------+---------------+---------------
Row 1          |A1             |B1
Row 2          |A2             |B2             '''.replaceAll(' ', '')
    }

}
